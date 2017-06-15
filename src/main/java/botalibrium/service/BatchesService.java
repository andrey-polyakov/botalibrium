package botalibrium.service;

import botalibrium.dta.Page;
import botalibrium.dta.pricing.BatchPriceEstimation;
import botalibrium.dta.pricing.SellPriceEstimation;
import botalibrium.entity.Batch;
import botalibrium.entity.base.CustomFieldGroup;
import botalibrium.entity.embedded.records.Record;
import botalibrium.entity.embedded.containers.Container;
import botalibrium.service.exception.ServiceException;
import botalibrium.service.exception.ValidationException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
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
        for (Container pg : batch.getContainers()) {
            for (CustomFieldGroup cfg : pg.getCustomFields()) {
                customFieldsService.validate(cfg, Batch.class.getSimpleName());
            }
            for (Record r : pg.getRecords()) {
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

    public Page basicSearch(String query, int page, int limit) throws ValidationException {
        int defaultLimit = 25;
        if (limit > 0) {
            defaultLimit = limit;
        }
        Query<Batch> q = batches.createQuery();
        List<String> tags = new ArrayList<>();
        tags.add(query);
        if (query != null && !query.isEmpty()) {
            q.or(
                    q.criteria("material.taxon").containsIgnoreCase(query),
                    q.criteria("containers.tag").containsIgnoreCase(query));
        }
        List<Batch> list = q.order().asList(new FindOptions().skip(page - 1).limit(defaultLimit));
        return new Page(list, page);
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
        for (Container container : batch.getContainers()) {
            long overridenProfit = netProfit;
            if (netProfit == 0) {
                overridenProfit = round(container.getPlantSize().getGenericPrice() * batch.getMaterial().getProductionDifficulty().getCoefficient());
            }
            long expense = batch.getMaterial().getBuyPrice() / batch.getCount();
            Map<String, Double> rates = new TreeMap<>();
            rates.put("USD/CAD", 1.32661183);
            rates.put("EUR/CAD", 1.48521491);
            PayPalFeeCalculator calc = new PayPalFeeCalculator(overridenProfit, shippingCost, expense, rates);
            List<SellPriceEstimation> estimates = new ArrayList<>();
            estimates.add(calc.estimatePriceCAD());
            result.getContainers().put(container.getPlantSize(), estimates);
        }
        return result;
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
