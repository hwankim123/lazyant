package com.hwan.lazyant.model.asset;

import com.hwan.lazyant.model.trading.Trading;
import jakarta.annotation.Nonnull;
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

    public StockAsset(@Nonnull Trading trading) {
        this.stockId = trading.getStockId();
        this.volume = trading.provideSignedVolume();
        this.investmentPrincipal = trading.calculateAmount();
    }

    public void accumulate(Trading trading) {
        this.volume += trading.provideSignedVolume();
        this.investmentPrincipal += trading.calculateAmount();
    }

    public void mapPortfolioItem(Long portfolioItemId) {
        this.portfolioItemId = portfolioItemId;
    }
}
