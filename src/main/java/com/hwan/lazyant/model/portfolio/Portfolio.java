package com.hwan.lazyant.model.portfolio;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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

    //TODO: 네이밍
    public static Portfolio withUnknownFactor(String name, List<PortfolioItem> items) {
        Portfolio portfolio = new Portfolio();
        portfolio.name = name;
        items.add(new PortfolioItem(Factor.UNKNOWN, 0));
        items.forEach(item -> item.mapWithPortfolio(portfolio));
        return portfolio;
    }

    public List<PortfolioItem> getItems() {
        return items;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public PortfolioItem getUnknownFactorItem() {
        return this.items.stream()
                .filter(PortfolioItem::isFactorUnknown)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Cannot get PortfolioItem with Factor=%s".formatted(Factor.UNKNOWN)));
    }
}
