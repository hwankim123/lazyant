package com.hwan.lazyant.openapi.market;

import com.hwan.lazyant.model.stock.type.Market;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarketPriceRequest {
    private Market market;
    private String symbol;
}
