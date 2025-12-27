package com.hwan.lazyant.repository.asset.projection;

import com.hwan.lazyant.model.stock.type.Market;
import com.hwan.lazyant.util.StockValueCalculator;

public record MarketEvaluatedStockAssetDetails(
        StockAssetDetails stockAssetDetails,
        double marketPrice, // 현재가
        double evaluatedAmount, // 평가금액
        double profitLoss, // 수익/손익금
        double profitLossRate // 수익/손익률
) {

    public Long getId() {
        return stockAssetDetails.id();
    }

    public String getName() {
        return stockAssetDetails.name();
    }

    public String getSymbol() {
        return stockAssetDetails.symbol();
    }

    public double getAveragePrice() {
        return stockAssetDetails.averagePrice();
    }

    public double getPrincipal() {
        return stockAssetDetails.principal();
    }

    public double getQuantity() {
        return stockAssetDetails.quantity();
    }

    public record StockAssetDetails(
            Long id,
            Long stockId,
            String name,
            Market market,
            String symbol,
            double principal,
            double quantity,
            double averagePrice) {

        public static StockAssetDetails from(Long id, Long stockId, String name, Market market, String symbol,
                                             double principal, double quantity
        ) {
            return new StockAssetDetails(
                    id,
                    stockId,
                    name,
                    market,
                    symbol,
                    principal,
                    quantity,
                    StockValueCalculator.calculateAveragePrice(principal, quantity, 2)
            );
        }

        public MarketEvaluatedStockAssetDetails evaluate(double marketPrice) {
            return new MarketEvaluatedStockAssetDetails(
                    this,
                    marketPrice,
                    this.evaluateEvaluatedAmount(marketPrice),
                    this.evaluateProfitLoss(marketPrice),
                    this.evaluateProfitLossRate(marketPrice)
            );
        }

        private double evaluateEvaluatedAmount(double marketPrice) {
            return StockValueCalculator.calculateAmount(marketPrice, quantity, 2);
        }

        private double evaluateProfitLoss(double marketPrice) {
            return StockValueCalculator.calculateProfitLoss(this.evaluateEvaluatedAmount(marketPrice), principal);
        }

        private double evaluateProfitLossRate(double marketPrice) {
            return StockValueCalculator.calculateProfitLossRate(this.evaluateEvaluatedAmount(marketPrice), principal, 2);
        }
    }
}
