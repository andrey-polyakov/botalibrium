package botalibrium.dta.output.pricing;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by apolyakov on 6/12/2017.
 */
public class SellPriceEstimation {
    private long netProfit = 0;
    private long itemPrice = 0;
    private long usAdjustment = 0;
    private long euroAdjustment = 0;

    private Map<String, Long> costs = new TreeMap<>();

    public long getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(long netProfit) {
        this.netProfit = netProfit;
    }

    public long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Map<String, Long> getCosts() {
        return costs;
    }

    public void setCosts(Map<String, Long> costs) {
        this.costs = costs;
    }

    public long getUsAdjustment() {
        return usAdjustment;
    }

    public void setUsAdjustment(long usAdjustment) {
        this.usAdjustment = usAdjustment;
    }

    public long getEuroAdjustment() {
        return euroAdjustment;
    }

    public void setEuroAdjustment(long euroAdjustment) {
        this.euroAdjustment = euroAdjustment;
    }
}
