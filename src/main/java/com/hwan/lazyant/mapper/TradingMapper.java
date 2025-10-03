package com.hwan.lazyant.mapper;

import com.hwan.lazyant.controller.trading.dto.TradingInsertRequest;
import com.hwan.lazyant.model.Trading;

public class TradingMapper {

    public static Trading mapToTrading(TradingInsertRequest request) {
        return new Trading(request.getStockId(), request.getAccountId(), request.getTradingType(), request.getMarketCurrentPrice(),
                request.getTradeTime(), request.getVolume(), request.getMemo());
    }
}
