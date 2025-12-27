package com.hwan.lazyant.repository.asset;

import com.hwan.lazyant.model.asset.StockAsset;
import com.hwan.lazyant.repository.asset.projection.StockAssetDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockAssetRepository extends JpaRepository<StockAsset, Long> {

    Optional<StockAsset> findByStockId(Long stockId);

    @Query(value = """
            select sa.id as id,
                   s.id as stockId,
                   s.name as name,
                   s.market as market,
                   s.symbol as symbol,
                   ROUND(sa.principal::numeric, 2) as principal,
                   ROUND(sa.quantity::numeric, 6) as quantity
            from la_stock_assets sa
            inner join la_stocks s on sa.stock_id = s.id
            where sa.id = :id
            """, nativeQuery = true)
    StockAssetDetailsProjection findStockAssetDetailsById(@Param("id") Long id);
}
