package com.hwan.lazyant.controller.trading.dto.response;

import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.model.trading.TradingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TradingResponse {

    private LocalDateTime tradeAt;
    private TradingType tradingType;
    private Double price;
    private Double quantity;
    private Double amount;

    public static TradingResponse from(Trading trading) {
        return new TradingResponse(
                trading.getTradeAt(),
                trading.getTradingType(),
                trading.getPrice(),
                trading.getQuantity(),
                trading.evaluateAmount()
        );
    }
}
