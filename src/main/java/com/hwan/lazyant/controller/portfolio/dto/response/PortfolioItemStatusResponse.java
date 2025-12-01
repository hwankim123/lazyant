package com.hwan.lazyant.controller.portfolio.dto.response;

import com.hwan.lazyant.model.portfolio.Factor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioItemStatusResponse {
    private Long id;
    private Factor factor;
    private double totalPrincipal;
    private double totalAmount;
    private double totalProfitLoss;
    private double totalProfitLossRate;
    private double totalWeight;
    private List<PortfolioHoldingResponse> holdings = new ArrayList<>();

    public PortfolioItemStatusResponse(PortfolioHoldingResponse holding) {
        this.id = holding.getPortfolioItemId();
        this.factor = holding.getFactor();
        this.totalPrincipal = holding.getPrincipal();
        this.totalAmount = holding.getEvaluatedAmount();
        this.holdings.add(holding);
    }

    public void accumulate(PortfolioHoldingResponse holding) {
        this.holdings.add(holding);
        this.totalAmount += holding.getEvaluatedAmount();
        this.totalPrincipal += holding.getPrincipal();
    }

    public void calculateTotalProfitLoss() {
        this.totalProfitLoss = this.totalAmount - totalPrincipal;
        this.totalProfitLossRate = ((this.totalProfitLoss / totalPrincipal) * 100 * 100) / 100.0;
    }

    public void calculateWeight(double totalAmount) {
        this.totalWeight = ((this.totalAmount / totalAmount) * 100 * 100) / 100.0;
        this.holdings.forEach(holding -> holding.calculateWeight(totalAmount));
    }
}
