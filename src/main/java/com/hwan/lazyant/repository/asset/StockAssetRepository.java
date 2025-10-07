package com.hwan.lazyant.repository.asset;

import com.hwan.lazyant.model.asset.StockAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockAssetRepository extends JpaRepository<StockAsset, Long> {

    Optional<StockAsset> findByStockId(Long stockId);
}
