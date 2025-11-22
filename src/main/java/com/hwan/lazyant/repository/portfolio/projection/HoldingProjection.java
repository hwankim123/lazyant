package com.hwan.lazyant.repository.portfolio.projection;

import com.hwan.lazyant.model.portfolio.Holding;
import com.hwan.lazyant.model.stock.type.Market;

public interface HoldingProjection {
    String getSymbol();
    Market getMarket();
    double getTotalInvestmentPrincipal();
    double getTotalVolume();

    default Holding toModel() {
        return new Holding(getSymbol(), getMarket(), getTotalInvestmentPrincipal(), getTotalVolume());
    }
}
