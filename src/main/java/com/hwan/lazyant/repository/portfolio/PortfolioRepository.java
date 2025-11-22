package com.hwan.lazyant.repository.portfolio;

import com.hwan.lazyant.model.portfolio.Portfolio;
import com.hwan.lazyant.repository.portfolio.projection.HoldingProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByUserId(long userId);

    @Query(value = """
            select s.symbol as symbol,
                   s.market as market,
                   sum(sa.investment_principal) as totalInvestmentPrincipal,
                   sum(sa.volume) as totalVolume
            from la_portfolios p
            inner join la_portfolio_items pi on p.id = pi.portfolio_id
            inner join la_stock_assets sa on pi.id = sa.portfolio_item_id
            inner join la_stocks s on sa.stock_id = s.id
            group by s.symbol, s.market
            """, nativeQuery = true)
    List<HoldingProjection> findHoldingsByUserId(long userId);
}
