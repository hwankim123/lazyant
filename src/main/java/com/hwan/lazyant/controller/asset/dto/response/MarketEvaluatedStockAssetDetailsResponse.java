package com.hwan.lazyant.controller.asset.dto.response;

import com.hwan.lazyant.controller.trading.dto.response.TradingResponse;
import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.repository.asset.projection.MarketEvaluatedStockAssetDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarketEvaluatedStockAssetDetailsResponse {
    private Long id;
    private String name;
    private String symbol;

    private double quantity; // 보유 수량
    private double principal; // 투자 원금
    private double averagePrice; // 평단가

    private double evaluatedAmount; // 평가금액
    private double marketPrice; // 시장가
    private double profitLoss; // 총 수익금
    private double profitLossRate; // 수익률

    private List<TradingResponse> recentTradings;

    public MarketEvaluatedStockAssetDetailsResponse(
            MarketEvaluatedStockAssetDetails marketEvaluatedStockAssetDetails,
            List<Trading> recentTradings
    ) {
        this.id = marketEvaluatedStockAssetDetails.getId();
        this.name = marketEvaluatedStockAssetDetails.getName();
        this.symbol = marketEvaluatedStockAssetDetails.getSymbol();
        this.quantity = marketEvaluatedStockAssetDetails.getQuantity();
        this.principal = marketEvaluatedStockAssetDetails.getPrincipal();
        this.averagePrice = marketEvaluatedStockAssetDetails.getAveragePrice();
        this.evaluatedAmount = marketEvaluatedStockAssetDetails.evaluatedAmount();
        this.marketPrice = marketEvaluatedStockAssetDetails.marketPrice();
        this.profitLoss = marketEvaluatedStockAssetDetails.profitLoss();
        this.profitLossRate = marketEvaluatedStockAssetDetails.profitLossRate();
        this.recentTradings = recentTradings.stream()
                .map(TradingResponse::from)
                .toList();
    }
}
