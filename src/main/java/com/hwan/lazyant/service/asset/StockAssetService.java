package com.hwan.lazyant.service.asset;

import com.hwan.lazyant.model.asset.StockAsset;
import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.repository.asset.StockAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockAssetService {

    private final StockAssetRepository stockAssetRepository;

    @Transactional
    public void accumulate(Trading trading) {
        StockAsset stockAsset = this.stockAssetRepository.findByStockId(trading.getStockId())
                .orElseGet(() -> new StockAsset(trading.getStockId()));
        stockAsset.accumulate(trading);
        stockAssetRepository.save(stockAsset);
    }
}
