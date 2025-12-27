package com.hwan.lazyant.repository.portfolio;

import com.hwan.lazyant.model.portfolio.Portfolio;
import com.hwan.lazyant.repository.portfolio.projection.HoldingProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByUserId(long userId);

    @Query(value = """
            select s.name as name,
                   s.market as market,
                   s.symbol as symbol,
                   pi.id as portfolioItemId,
                   pi.factor as factor,
                   sa.id as stockAssetId,
                   ROUND(sa.principal::numeric, 2) as principal,
                   ROUND(sa.quantity::numeric, 6) as quantity
            from la_portfolios p
            inner join la_portfolio_items pi on p.id = pi.portfolio_id
            left join la_stock_assets sa on pi.id = sa.portfolio_item_id
            inner join la_stocks s on sa.stock_id = s.id
            where p.user_id = :userId
            """, nativeQuery = true)
    List<HoldingProjection> findHoldingsByUserId(@Param("userId") long userId);
}
