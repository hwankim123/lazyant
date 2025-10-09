package com.hwan.lazyant.model.portfolio;

import com.hwan.lazyant.model.asset.StockAsset;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@Entity
@Table(name = "la_portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioItem> items = new ArrayList<>();

    public Portfolio(String name, List<PortfolioItem> items) {
        this.name = name;
        items.forEach(item -> item.mapWithPortfolio(this));
    }

    public void setPortfolioItem(PortfolioItem portfolioItem) {
        this.items.add(portfolioItem);
    }

    public void createUnknownFactorItemIfNotPresent() {
        this.getUnknownFactorItem()
                .ifPresentOrElse(
                        item -> log.debug("PortfolioItem with Factor:%s is present.".formatted(Factor.UNKNOWN)),
                        () -> new PortfolioItem(Factor.UNKNOWN, 0)
                                .mapWithPortfolio(this)
                );
    }

    public void addToUnknownItemFactor(StockAsset newStockAsset) {
        PortfolioItem portfolioItem = this.getUnknownFactorItem()
                .orElseThrow(() -> new IllegalStateException("Cannot get PortfolioItem with Factor=%s".formatted(Factor.UNKNOWN)));
        newStockAsset.mapPortfolioItem(portfolioItem.getId());
    }

    private Optional<PortfolioItem> getUnknownFactorItem() {
        return this.items.stream()
                .filter(PortfolioItem::isFactorUnknown)
                .findFirst();
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
