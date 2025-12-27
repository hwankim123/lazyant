package com.hwan.lazyant.repository.asset.projection;

import com.hwan.lazyant.model.stock.type.Market;

import static com.hwan.lazyant.repository.asset.projection.MarketEvaluatedStockAssetDetails.StockAssetDetails;

public interface StockAssetDetailsProjection {

    Long getId();
    Long getStockId();
    String getName();
    Market getMarket();
    String getSymbol();
    double getPrincipal();
    double getQuantity();

    default StockAssetDetails toModel() {
        return StockAssetDetails.from(getId(), getStockId(), getName(), getMarket(), getSymbol(), getPrincipal(), getQuantity());
    }
}
