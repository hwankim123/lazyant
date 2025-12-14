package com.hwan.lazyant.openapi.market;

import com.hwan.lazyant.model.stock.type.Market;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarketPriceRequest {
    private Market market;
    private String symbol;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketPriceRequest that = (MarketPriceRequest) o;
        return market == that.market && Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(market, symbol);
    }
}
