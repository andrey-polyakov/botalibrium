package botalibrium.entity;

/**
 * Created by apolyakov on 6/14/2017.
 */
public class Forex {
    private String symbol;
    private double rate;

    public Forex(String symbol, double rate) {
        this.symbol = symbol;
        this.rate = rate;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getRate() {
        return rate;
    }
}
