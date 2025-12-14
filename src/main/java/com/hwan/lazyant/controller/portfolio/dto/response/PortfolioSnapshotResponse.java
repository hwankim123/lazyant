package com.hwan.lazyant.controller.portfolio.dto.response;

import com.hwan.lazyant.model.portfolio.PortfolioSnapshot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioSnapshotResponse {
    private double totalAmount;
    private double totalProfitLoss;
    private double totalProfitLossRate;
    private List<PortfolioItemSnapshotResponse> itemSnapshots;

    public PortfolioSnapshotResponse(PortfolioSnapshot portfolioSnapshot) {
        this.totalAmount = portfolioSnapshot.getTotalAmount();
        this.totalProfitLoss = portfolioSnapshot.getTotalProfitLoss();
        this.totalProfitLossRate = portfolioSnapshot.getTotalProfitLossRate();
        this.itemSnapshots = portfolioSnapshot.getItemSnapshots().stream()
                .map(PortfolioItemSnapshotResponse::new)
                .toList();
    }
}
