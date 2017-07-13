package botalibrium.service;

import botalibrium.dta.input.bulk.CountLogDto;
import botalibrium.dta.input.bulk.InsertRecordsInBulk;
import botalibrium.dta.input.bulk.UnpopulatedBatch;
import botalibrium.dta.input.bulk.UnpopulatedContainer;
import botalibrium.dta.output.Page;
import botalibrium.dta.output.bulk.BulkOperationPreview;
import botalibrium.dta.output.pricing.BatchPriceEstimation;
import botalibrium.dta.output.pricing.SellPriceEstimation;
import botalibrium.entity.Batch;
import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.embedded.containers.PlantsContainer;
import botalibrium.entity.embedded.containers.SeedsCommunityContainer;
import botalibrium.entity.embedded.records.Record;
import botalibrium.references.SizeChart;
import botalibrium.rest.BatchesEndpoint;
import botalibrium.service.exception.ServiceException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static java.lang.Math.round;


@Component
public class BatchesService {
    @Autowired
    private BasicDAO<Batch, ObjectId> batches;
    @Autowired
    private CustomFieldsService customFieldsService;


    public Key<Batch> save(Batch batch) throws ServiceException {
        for (CustomFieldGroup cfg : batch.getCustomFieldGroups()) {
            customFieldsService.validate(cfg, Batch.class.getSimpleName());
        }
        Set<String> batchTags = new TreeSet<>();
        for (PlantsContainer container : batch.getContainers()) {
            if (!batchTags.add(container.getTag())) {
                throw new ServiceException("Duplicate tags within batch are not allowed");
            }
            for (CustomFieldGroup cfg : container.getCustomFields()) {
                customFieldsService.validate(cfg, Batch.class.getSimpleName());
            }
            for (Record r : container.getRecords()) {
                for (CustomFieldGroup cfg : r.getCustomFields()) {
                    customFieldsService.validate(cfg, Batch.class.getSimpleName());
                }
            }
        }
        return batches.save(batch);
    }

    public void addRecord(ObjectId id, Record r) throws ServiceException {
        Batch c = batches.get(id);
        if (c == null) {
            throw new ServiceException("Record not found");
        }
        for (CustomFieldGroup field : r.getCustomFields()) {
            customFieldsService.validate(field, Record.class.getSimpleName());
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

    public Batch getContainer(ObjectId id) throws ServiceException {
        Batch c = batches.get(id);
        if (c == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return c;
    }

    public BatchPriceEstimation calculatePrice(ObjectId id, long netProfit, long shippingCost) throws ServiceException {
        Batch batch = batches.get(id);
        if (batch == null) {
            throw new ServiceException("Record not found");
        }
        BatchPriceEstimation result = new BatchPriceEstimation(batch);
        for (PlantsContainer container : batch.getContainers()) {
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
            for (PlantsContainer c : batch.getContainers()) {
                BulkOperationPreview.PreviewItem pi = new BulkOperationPreview.PreviewItem();
                if (!operation.getTagsToCountLog().containsKey(c.getTag())) {
                    continue;
                }
                CountLogDto count = operation.getTagsToCountLog().get(c.getTag());
                if (c instanceof PlantsContainer) {
                    if (!operation.isPreview() && count != null) {
                        ((PlantsContainer) c).addCountLog(PlantsContainer.fromDto(count));
                    }
                    if (!((PlantsContainer) c).getCountLogs().isEmpty()) {
                        pi.setLatestCountLog(((PlantsContainer) c).getCountLogs().getLast().toDto());
                    }
                }
                if (c instanceof SeedsCommunityContainer) {
                    if (!operation.isPreview() && count != null) {
                        ((SeedsCommunityContainer) c).addCountLog(SeedsCommunityContainer.fromDto(count));
                    }
                    if (!((SeedsCommunityContainer) c).getCountLogs().isEmpty()) {
                        pi.setLatestCountLog(((SeedsCommunityContainer) c).getCountLogs().getLast().toDto());
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
                Container c;
                if (uc.getType().equals("SeedsCommunityContainer")) {
                    c = new SeedsCommunityContainer();
                } else if (uc.getType().equals("PlantsContainer")) {
                    c = new PlantsContainer();
                } else {
                    c = new Container();
                }
                c.setDescription(uc.getDescription());
                c.setMedia(uc.getMedia());
                c.setTag(uc.getTag().replace("*", String.valueOf(counter++)));
                c.setPlantSize(SizeChart.NA);
                newBatch.getContainers().add(c);
            }
        }
        return save(newBatch);
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
