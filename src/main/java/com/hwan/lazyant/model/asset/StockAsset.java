package com.hwan.lazyant.model.asset;

import com.hwan.lazyant.model.portfolio.Portfolio;
import com.hwan.lazyant.model.portfolio.PortfolioItem;
import com.hwan.lazyant.model.trading.Trading;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "la_stock_assets")
public class StockAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    private Long stockId;

    private Long portfolioItemId;

    private double volume;

    private double investmentPrincipal;

    public StockAsset(Long stockId) {
        this(stockId, null, 0d, 0d);
    }

    public StockAsset(@Nonnull Long stockId, @Nullable Long portfolioItemId, @Nullable double volume, @Nullable double investmentPrincipal) {
        this.stockId = stockId;
        this.portfolioItemId = portfolioItemId;
        this.volume = volume;
        this.investmentPrincipal = investmentPrincipal;
    }

    public static StockAsset from(@Nonnull Trading trading) {
        StockAsset stockAsset = new StockAsset();
        stockAsset.stockId = trading.getStockId();
        stockAsset.volume = trading.getSignedQuantity();
        stockAsset.investmentPrincipal = trading.evaluateAmount();
        return stockAsset;
    }

    public void accumulate(Trading trading) {
        this.volume += trading.getSignedQuantity();
        this.investmentPrincipal += trading.evaluateAmount();
    }

    public void mapPortfolioItem(Portfolio portfolio) {
        PortfolioItem unknownFactor = portfolio.getUnknownFactorItem();
        this.portfolioItemId = unknownFactor.getId();
    }

    public double getVolume() {
        return this.volume;
    }

    public double getInvestmentPrincipal() {
        return investmentPrincipal;
    }
}
