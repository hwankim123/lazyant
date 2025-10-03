package com.hwan.lazyant.controller.trading.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hwan.lazyant.model.trading.TradingType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TradingInsertRequest {
    private Long stockId;
    private Long accountId;
    private TradingType tradingType;
    private Double marketCurrentPrice;
    private Double volume;
    private Double tradePrice;
    private String memo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tradeTime;
}
