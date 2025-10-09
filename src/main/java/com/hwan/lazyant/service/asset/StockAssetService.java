package com.hwan.lazyant.service.asset;

import com.hwan.lazyant.model.asset.StockAsset;
import com.hwan.lazyant.model.portfolio.Portfolio;
import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.repository.asset.StockAssetRepository;
import com.hwan.lazyant.service.portfolio.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockAssetService {

    private final StockAssetRepository stockAssetRepository;
    private final PortfolioService portfolioService;

    @Transactional
    public void accumulate(Trading trading) {
        this.stockAssetRepository.findByStockId(trading.getStockId())
                .ifPresentOrElse(
                        stockAsset -> stockAsset.accumulate(trading),
                        () -> this.init(trading)
                );
    }

    private void init(Trading trading) {
        StockAsset newStockAsset = new StockAsset(trading);
        Portfolio portfolio = portfolioService.findByUserId(1L);// TODO: 개인화
        portfolio.addToUnknownItemFactor(newStockAsset);
        stockAssetRepository.save(newStockAsset);
    }
}
