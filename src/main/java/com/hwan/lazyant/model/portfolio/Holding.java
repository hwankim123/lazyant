package com.hwan.lazyant.model.portfolio;

import com.hwan.lazyant.model.stock.type.Market;
import lombok.Getter;

@Getter
public class Holding {
    private String symbol;
    private Market market;
    private double totalInvestmentPrincipal;
    private double totalVolume;
    private double averagePrice;
    private double marketPrice;
    private double profitLoss;
    private double profitLossRate;

    public Holding(String symbol, Market market, double totalInvestmentPrincipal, double totalVolume) {
        this.symbol = symbol;
        this.market = market;
        this.totalInvestmentPrincipal = totalInvestmentPrincipal;
        this.totalVolume = totalVolume;
        this.averagePrice = this.calculateAveragePrice();
    }

    private double calculateAveragePrice() {
        return Math.round(this.totalInvestmentPrincipal / this.totalVolume * 100) / 100.0;
    }

    public Holding withMarketEvaluation(double marketPrice) {
        this.marketPrice = marketPrice;
        this.profitLoss = this.totalInvestmentPrincipal - (marketPrice * totalVolume);
        this.profitLossRate = Math.round((this.profitLoss / totalInvestmentPrincipal) * 100 * 100) / 100.0;
        return this;
    }
}
