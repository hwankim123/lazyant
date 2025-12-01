package com.hwan.lazyant.model.portfolio;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "la_portfolio_items")
public class PortfolioItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Nonnull
    @Enumerated(value = EnumType.STRING)
    private Factor factor;

    private Integer weight;

    public PortfolioItem(@Nonnull Factor factor, Integer weight) {
        this.factor = factor;
        this.weight = weight;
    }

    public void mapWithPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
        portfolio.getItems().add(this);
    }

    public boolean isFactorUnknown() {
        return this.factor.equals(Factor.UNKNOWN);
    }
}
