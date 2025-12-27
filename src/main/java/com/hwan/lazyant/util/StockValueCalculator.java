package com.hwan.lazyant.util;

public class StockValueCalculator {

    public static double calculateAmount(double price, double quantity, int decimalPlace) {
        return roundWith(quantity * price, decimalPlace);
    }

    public static double calculateAveragePrice(double principal, double quantity, int decimalPlace) {
        return roundWith(principal / quantity, decimalPlace);
    }

    public static double calculateProfitLoss(double evaluatedAmount, double principal) {
        return evaluatedAmount - principal;
    }

    public static double calculateProfitLossRate(double evaluatedAmount, double principal, int decimalPlace) {
        double profitLoss = calculateProfitLoss(evaluatedAmount, principal);
        return roundWith(profitLoss / principal * 100.0, decimalPlace);
    }

    public static double calculateWeight(double evaluatedAmount, double totalAmount, int decimalPlace) {
        return roundWith(evaluatedAmount / totalAmount * 100.0, decimalPlace) ;
    }

    private static double roundWith(double value, int decimalPlace) {
        double pow = Math.pow(10, decimalPlace);
        return Math.round(value) * pow / pow;
    }
}
