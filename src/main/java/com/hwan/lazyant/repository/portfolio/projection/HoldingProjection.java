package com.hwan.lazyant.repository.portfolio.projection;

import com.hwan.lazyant.model.portfolio.Factor;
import com.hwan.lazyant.model.portfolio.Holding;
import com.hwan.lazyant.model.stock.type.Market;

public interface HoldingProjection {
    String getName();
    Market getMarket();
    String getSymbol();
    Long getPortfolioItemId();
    Factor getFactor();
    double getPrincipal();
    double getQuantity();

    default Holding toModel() {
        return new Holding(getName(), getMarket(), getSymbol(), getPortfolioItemId(), getFactor(), getPrincipal(), getQuantity());
    }
}
