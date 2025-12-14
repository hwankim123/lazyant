package com.hwan.lazyant.model.portfolio;

import com.hwan.lazyant.util.StockValueCalculator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 포트폴리오 현황
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioSnapshot {
    private double totalAmount; // 자산 총액
    private double totalPrincipal; // 총 투자원금
    private double totalProfitLoss; // 총 수익금
    private double totalProfitLossRate; // 수익률
    private List<PortfolioItemSnapshot> itemSnapshots = new ArrayList<>();

    public static PortfolioSnapshot gatherAll(Collection<PortfolioItemSnapshot> itemSnapshots) {
        PortfolioSnapshot portfolioSnapshot = new PortfolioSnapshot();
        for (PortfolioItemSnapshot itemSnapshot : itemSnapshots) {
            portfolioSnapshot.gather(itemSnapshot);
        }
        itemSnapshots.forEach(itemStatus -> itemStatus.calculateWeight(portfolioSnapshot.getTotalAmount()));
        portfolioSnapshot.calculateProfitLossAndRate();
        return portfolioSnapshot;
    }

    private void gather(PortfolioItemSnapshot itemSnapshot) {
        this.itemSnapshots.add(itemSnapshot);
        itemSnapshot.calculateProfitLossAndRate();
        this.totalPrincipal += itemSnapshot.getPrincipal();
        this.totalAmount += itemSnapshot.getEvaluatedAmount();
    }

    private void calculateProfitLossAndRate() {
        this.totalProfitLoss = StockValueCalculator.calculateProfitLoss(this.totalAmount, this.totalPrincipal);
        this.totalProfitLossRate = StockValueCalculator.calculateProfitLossRate(this.totalAmount, this.totalPrincipal, 2);
    }

}
