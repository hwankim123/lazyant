package com.hwan.lazyant.controller.portfolio.dto.response;

import com.hwan.lazyant.model.portfolio.Factor;
import com.hwan.lazyant.model.portfolio.PortfolioItemSnapshot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioItemSnapshotResponse {
    private Long id;
    private Factor factor;
    private double principal;
    private double evaluatedAmount;
    private double profitLoss;
    private double profitLossRate;
    private double weight;
    private List<MarketEvaluatedHoldingResponse> holdings;

    public PortfolioItemSnapshotResponse(PortfolioItemSnapshot itemSnapshot) {
        this.id = itemSnapshot.getId();
        this.factor = itemSnapshot.getFactor();
        this.principal = itemSnapshot.getPrincipal();
        this.evaluatedAmount = itemSnapshot.getEvaluatedAmount();
        this.weight = itemSnapshot.getWeight();
        this.holdings = itemSnapshot.getHoldings().stream()
                .map(MarketEvaluatedHoldingResponse::new)
                .toList();
    }
}
