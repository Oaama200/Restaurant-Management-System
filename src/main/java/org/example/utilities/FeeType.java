package org.example.utilities;

public enum FeeType {
    DISCOUNT_RATE(0.15),
    TAX_RATE(0.06),
    SERVICE_FEE(0.05),
    DELIVERY_FEE(0.10);

    private final double rate;
    FeeType(double rate) {
        this.rate = rate;
    }
    public double getRate() {
        return rate;
    }
}