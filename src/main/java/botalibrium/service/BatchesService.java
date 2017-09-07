package botalibrium.service;

import botalibrium.dto.input.bulk.InsertRecordsInBulk;
import botalibrium.dto.input.bulk.UnpopulatedBatch;
import botalibrium.dto.input.bulk.UnpopulatedContainer;
import botalibrium.dto.output.BatchDto;
import botalibrium.dto.output.Page;
import botalibrium.dto.output.bulk.BulkOperationPreview;
import botalibrium.dto.output.pricing.BatchPriceEstimation;
import botalibrium.dto.output.pricing.SellPriceEstimation;
import botalibrium.entity.Batch;
import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.embedded.containers.EmptyContainer;
import botalibrium.entity.embedded.containers.PlantsContainer;
import botalibrium.entity.embedded.containers.SeedsContainer;
import botalibrium.entity.embedded.containers.TemporalStringTuple;
import botalibrium.entity.embedded.records.Record;
import botalibrium.rest.BatchesEndpoint;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import botalibrium.service.temporal.TemporalVariableHelper;
import com.mongodb.*;
import lombok.extern.log4j.Log4j;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static java.lang.Math.round;

@Log4j
@Component
public class BatchesService {
    @Autowired
    private BasicDAO<Batch, ObjectId> batches;
    @Autowired
    private CustomFieldsService customFieldsService;
    @Autowired
    Datastore ds;


    public Batch updateBatch(ObjectId id, Batch batch) throws ServiceException {
        Query<Batch> updateQuery = batches.getDatastore().createQuery(Batch.class).field("_id").equal(id);
        UpdateOperations<Batch> ops = batches.getDatastore().createUpdateOperations(Batch.class).set("containers", batch.getContainers());
        batches.update(updateQuery, ops);

        ops = batches.getDatastore().createUpdateOperations(Batch.class).set("material", batch.getMaterial());
        batches.update(updateQuery, ops);

        ops = batches.getDatastore().createUpdateOperations(Batch.class).set("labels", batch.getLabels());
        batches.update(updateQuery, ops);

        ops = batches.getDatastore().createUpdateOperations(Batch.class).set("records", batch.getRecords());
        batches.update(updateQuery, ops);

        ops = batches.getDatastore().createUpdateOperations(Batch.class).set("started", batch.getStarted());
        batches.update(updateQuery, ops);
        return getBatch(id);
    }

    public Key<Batch> save(Batch batch) throws ServiceException {
        for (CustomFieldGroup cfg : batch.getCustomFieldGroups()) {
            customFieldsService.validate(cfg, Batch.class.getSimpleName());
        }
        Set<String> batchTags = new TreeSet<>();
        for (EmptyContainer container : batch.getContainers()) {
            if (!batchTags.add(container.getTag())) {
                throw new ValidationException("Duplicate tags within batch not allowed");
            }
            TemporalVariableHelper.align(container.getMedia());
        }
        try {
            return batches.save(batch);
        } catch (DuplicateKeyException e) {
            throw new ServiceException(e.getMessage(), Response.Status.CONFLICT);
        }
    }

    public void addRecord(ObjectId id, Record r) throws ServiceException {
        Batch c = batches.get(id);
        if (c == null) {
            throw new ServiceException("Record not found");
        }
        c.getRecords().add(r);
    }

    public void delete(Batch c) throws ServiceException {
        batches.delete(c);
    }

    public Page basicSearch(String query, long page, long limit, UriInfo uriInfo) {
        long defaultLimit = 25;
        if (limit > 0) {
            defaultLimit = limit;
        }
        Query<Batch> q = batches.createQuery();
        List<String> tags = new ArrayList<>();
        tags.add(query);
        if (query != null && !query.isEmpty()) {
            q.or(q.criteria("material.taxon").containsIgnoreCase(query),
                    q.criteria("containers.tag").containsIgnoreCase(query));
        }
        List<Batch> list = q.order().asList(new FindOptions().skip((int) (page * defaultLimit)).limit((int) defaultLimit));
        return new Page(query, list, page, defaultLimit, q.count(), uriInfo);
    }
    public Page mediaSearch(String media, Date from, Date to, long page, long limit) {
        if (to.before(from)) {
            throw new ValidationException("Invalid arguments: effectiveTo is before effectiveFrom");
        }
        return mediaSearch(media, from, to, page, limit, null);
    }

    public Page all(long page, long limit) {
        long defaultLimit = 25;
        if (limit > 0) {
            defaultLimit = limit;
        }
        return new Page("", batches.find().asList(), page, defaultLimit,  batches.find().count(), null);
    }

    public void truncate() {
        ds.getDB().getCollection("Batch").drop();
    }

    public Page mediaSearch(String media, Date from, Date to, long page, long limit, UriInfo uriInfo) {
        long defaultLimit = 25;
        if (limit > 0) {
            defaultLimit = limit;
        }
        DBCollection collection = ds.getDB().getCollection("Batch");
        BasicDBObject effectiveFrom;
        effectiveFrom = new BasicDBObject("containers.media.effectiveFrom", new BasicDBObject("$lte", from));
        BasicDBObject effectiveTo;
        effectiveTo = new BasicDBObject("containers.media.effectiveTo", new BasicDBObject("$gte", to));
        BasicDBObject value = new BasicDBObject("containers.media.value", new BasicDBObject("$eq", media));

        BasicDBList and = new BasicDBList();
        and.add(effectiveFrom);
        and.add(effectiveTo);
        and.add(value);
        DBObject query = new BasicDBObject("$and", and);
        Morphia m = new Morphia();
        m.map(Batch.class);
        try (DBCursor output = collection.find(query)) {
            List<EmptyContainer> c = new ArrayList<>();
            while (output.hasNext()) {
                Collection<EmptyContainer> containers = extract(m.fromDBObject(ds, Batch.class, output.next()));
                for (EmptyContainer container : containers) {
                    for (TemporalStringTuple tuple : container.getMedia()) {
                        if (tuple.getEffectiveFrom().compareTo(from) <= 0 && tuple.getEffectiveTo().compareTo(to) >= 0) {
                            c.add(container);
                        }
                    }
                }
            }
            return new Page(media, c, page, defaultLimit, c.size(), uriInfo);
        } catch (MongoException me) {
            log.error("Search failed", me);
            throw new ServiceException(me.toString());
        }
    }

    private Collection<EmptyContainer> extract(Batch batch) {
        List<EmptyContainer> containers = new ArrayList<>();
        for (EmptyContainer ec : batch.getContainers()) {
            ec.setBatchId(batch.getId());
            containers.add(ec);
        }
        return containers;
    }

    public Batch getBatch(ObjectId id) throws ServiceException {
        Batch c = batches.get(id);
        if (c == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return c;
    }

    public void removeBatch(ObjectId id) throws ServiceException {
        batches.deleteById(id);
    }

    public EmptyContainer getContainer(String id) throws ServiceException {
        Query<Batch> q = batches.createQuery();
        q.criteria("containers." + id).exists();
        Batch b = q.get();
        if (b == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        EmptyContainer c = null;
        for (EmptyContainer container : q.get().getContainers()) {
            if (container.getTag().equals(id)) {
                c = container;
                break;
            }
        }
        if (c == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        c.setBatchId(b.getId());
        return c;
    }


    public EmptyContainer getContainer(ObjectId id, String tag) throws ServiceException {
        Batch b = batches.get(id);
        if (b == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        EmptyContainer c = null;
        for (EmptyContainer container : b.getContainers()) {
            if (container.getTag().equals(id)) {
                c = container;
                break;
            }
        }
        if (c == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        c.setBatchId(b.getId());
        return c;
    }

    public BatchPriceEstimation calculatePrice(ObjectId id, long netProfit, long shippingCost) throws ServiceException {
        Batch batch = batches.get(id);
        if (batch == null) {
            throw new ServiceException("Record not found");
        }
        BatchPriceEstimation result = new BatchPriceEstimation(batch);
        for (EmptyContainer container : batch.getContainers()) {
            long overriddenProfit = netProfit;
            if (netProfit == 0) {
                overriddenProfit = round(container.getPlantSize().getGenericPrice() * batch.getMaterial().getProductionDifficulty().getCoefficient());
            }
            if (batch.getCount() == 0) {
                continue;
            }
            long expense = batch.getMaterial().getBuyPrice() / batch.getCount();
            Map<String, Double> rates = new TreeMap<>();
            rates.put("USD/CAD", 1.32661183);
            rates.put("EUR/CAD", 1.48521491);
            PayPalFeeCalculator calc = new PayPalFeeCalculator(overriddenProfit, shippingCost, expense, rates);
            result.getContainers().put(container.getPlantSize(), calc.estimatePriceCAD());
        }
        return result;
    }

    public BulkOperationPreview bulkSelect(InsertRecordsInBulk operation) throws URISyntaxException {
        Query<Batch> q = batches.createQuery();
        q.criteria("containers.tag").in(operation.getTagsToCountLog().keySet());
        List<Batch> list = q.order().asList();
        BulkOperationPreview preview = new BulkOperationPreview();
        Set<String> notFound = new HashSet<>(operation.getTagsToCountLog().keySet());
        Set<String> nothingToChange = new HashSet<>(operation.getTagsToCountLog().keySet());

        for (Batch batch : list) {
            Record batchLastRecord = null;
            if (!batch.getRecords().isEmpty()) {
                batchLastRecord = batch.getRecords().get(batch.getRecords().size() - 1);
            }
            for (EmptyContainer c : batch.getContainers()) {
                BulkOperationPreview.PreviewItem pi = new BulkOperationPreview.PreviewItem();
                if (!operation.getTagsToCountLog().containsKey(c.getTag())) {
                    continue;
                }
                BatchDto.PopulationLogDto count = operation.getTagsToCountLog().get(c.getTag());
                if (c instanceof PlantsContainer) {
                    if (!operation.isPreview() && count != null) {
                        ((PlantsContainer) c).addCountLog(count.toEntity());
                    }
                    if (!((PlantsContainer) c).getPopulationLogs().isEmpty()) {
                        pi.setLatestCountLog(((PlantsContainer) c).getPopulationLogs().getLast().toDto());
                    }
                }
                if (c instanceof SeedsContainer) {
                    if (!operation.isPreview() && count != null) {
                        ((SeedsContainer) c).addCountLog(SeedsContainer.fromDto(count));
                    }
                    if (!((SeedsContainer) c).getPopulationLogs().isEmpty()) {
                        pi.setLatestCountLog(((SeedsContainer) c).getPopulationLogs().getLast().toDto());
                    }
                }
                nothingToChange.remove(c.getTag());
                notFound.remove(c.getTag());
                Record containerLastRecord = null;
                if (!c.getRecords().isEmpty()) {
                    containerLastRecord = c.getRecords().get(c.getRecords().size() - 1);
                }
                TreeSet<Record> records = new TreeSet<>();
                if (batchLastRecord != null) {
                    records.add(batchLastRecord);
                    nothingToChange.remove(c.getTag());
                }
                if (containerLastRecord != null) {
                    records.add(containerLastRecord);
                    nothingToChange.remove(c.getTag());
                }
                if (!records.isEmpty()) {
                    pi.setLatestRecord(records.last());
                }
                pi.setTag(c.getTag());
                pi.setTaxon(batch.getMaterial().getTaxon());
                pi.getLinks().add(new URI(BatchesEndpoint.BASE_URI + "/" + batch.getId()).toString());
                pi.setMedia(c.getMedia());
                preview.getContainers().add(pi);
                if (!operation.isPreview() && operation.getRecordToBeInserted() != null) {
                    c.getRecords().add(operation.getRecordToBeInserted());
                }
            }
            if (!operation.isPreview()) {
                batches.save(batch);
            }
        }
        preview.setNotFoundItems(notFound);
        preview.setNothingToChangeItems(nothingToChange);
        return preview;
    }

    public Key<Batch> newUnpopulatedBatch(UnpopulatedBatch unpopulatedBatch) throws ServiceException {
        Batch newBatch = new Batch();
        newBatch.setMaterial(unpopulatedBatch.getMaterial());
        int counter = 0;
        for (UnpopulatedContainer uc : unpopulatedBatch.getUnpopulatedContainers()) {
            for (int ii = 0; ii < uc.getContainersCount(); ii++) {
                EmptyContainer c;
                if (uc.getType().equals("SeedsContainer")) {
                    c = new SeedsContainer();
                } else if (uc.getType().equals("PlantsContainer")) {
                    c = new PlantsContainer();
                } else {
                    throw new ValidationException("Unknown Container type").set("TYPE", uc.getType());
                }
                c.setDescription(uc.getDescription());
                c.setMedia(uc.getMedia());
                c.setTag(uc.getTag().replace("*", String.valueOf(counter++)));
                newBatch.getContainers().add(c);
            }
        }
        return save(newBatch);
    }

    public EmptyContainer updateContainer(String originalTag, EmptyContainer container) {
        Query<Batch> q = batches.createQuery();
        q.criteria("containers." + originalTag).exists();
        Batch b = q.get();
        if (b == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        EmptyContainer c = null;
        for (EmptyContainer emptyContainer : q.get().getContainers()) {
            if (emptyContainer.getTag().equals(originalTag)) {
                c = emptyContainer;
                break;
            }
        }        if (c == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        String tag = container.getTag();
        if (!tag.equals(originalTag)) {// Retagging happens here
            q.get().getContainers().remove(originalTag);
        }
        q.get().getContainers().add(container);
        batches.save(b);
        for (EmptyContainer emptyContainer : q.get().getContainers()) {
            if (emptyContainer.getTag().equals(originalTag)) {
                c = emptyContainer;
                break;
            }
        }
        c.setBatchId(b.getId());
        return c;
    }

    static class PayPalFeeCalculator {

        private long netProfit, shipping, initialCost;
        private double eBayMarginalFee = 0.10;
        private Map<String, Double> rates;

        public PayPalFeeCalculator(long netProfitCAD, long shipping, long initialCost, Map<String, Double> rates) {
            this.netProfit = netProfitCAD;
            this.shipping = shipping;
            this.initialCost = initialCost;
            this.rates = rates;
        }

        public SellPriceEstimation estimatePriceCAD() {
            SellPriceEstimation spe = new SellPriceEstimation();
            long payPalFlatFee = 30;
            double payPalMarginalFee = 0.029;
            long flatCosts = netProfit + initialCost + shipping + payPalFlatFee;
            long grossPrice = round(flatCosts / (1 - payPalMarginalFee - eBayMarginalFee));
            spe.getCosts().put("PayPal marginal fee", round(grossPrice * payPalMarginalFee));
            spe.getCosts().put("PayPal flat fee", payPalFlatFee);
            spe.getCosts().put("eBay final value fee", round(grossPrice * eBayMarginalFee));
            spe.getCosts().put("Acquisition cost per item", initialCost);
            spe.getCosts().put("Shipping", shipping);
            spe.setItemPrice(grossPrice);
            spe.setNetProfit(netProfit);
            spe.setEuroAdjustment(euroGross() - grossPrice);
            spe.setUsAdjustment(usGross() - grossPrice);
            return spe;
        }

        public long usGross() {
            double usdcad = rates.get("USD/CAD");
            long payPalFlatFee = round(30 * usdcad);
            double payPalMarginalFee = 0.037;
            double flatCosts = netProfit + initialCost + shipping + payPalFlatFee;
            return round(flatCosts / (1 - payPalMarginalFee - eBayMarginalFee));
        }

        public long euroGross() {
            double eurcad = rates.get("EUR/CAD");
            long payPalFlatFee = round(35 * eurcad);
            double payPalMarginalFee = 0.039;
            double flatCosts = netProfit + initialCost + shipping + payPalFlatFee;
            return round(flatCosts / (1 - payPalMarginalFee - eBayMarginalFee));
        }

    }


}
