package com.hwan.lazyant.util;

public class StockValueCalculator {

    public static double calculateEvaluatedAmount(double marketPrice, double quantity, int decimalPlace) {
        return roundWith(quantity * marketPrice, decimalPlace);
    }

    public static double calculateProfitLoss(double evaluatedAmount, double principal) {
        return evaluatedAmount - principal;
    }

    public static double calculateProfitLossRate(double evaluatedAmount, double principal, int decimalPlace) {
        double profitLoss = calculateProfitLoss(evaluatedAmount, principal);
        return roundWith(profitLoss / principal * 100.0, decimalPlace);
    }

    public static double calculateWeight(double evaluatedAmount, double totalAmount, int decimalPlace) {
        //        this.weight = ((this.evaluatedAmount / totalAmount) * 100 * 100) / 100.0;
        return roundWith(evaluatedAmount / totalAmount * 100.0, decimalPlace) ;
    }

    private static double roundWith(double value, int decimalPlace) {
        double pow = Math.pow(10, decimalPlace);
        return Math.round(value) * pow / pow;
    }
}
