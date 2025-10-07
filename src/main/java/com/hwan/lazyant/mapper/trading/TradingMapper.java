package com.hwan.lazyant.mapper.trading;

import com.hwan.lazyant.controller.trading.dto.TradingInsertRequest;
import com.hwan.lazyant.model.trading.Trading;

public class TradingMapper {

    public static Trading mapToTrading(TradingInsertRequest request) {
        return new Trading(request.getStockId(), request.getAccountId(), request.getTradingType(), request.getPrice(),
                request.getTradeTime(), request.getVolume(), request.getMemo());
    }
}
