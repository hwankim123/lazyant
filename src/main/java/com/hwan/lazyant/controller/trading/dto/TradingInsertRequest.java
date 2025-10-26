package com.hwan.lazyant.controller.trading.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hwan.lazyant.model.trading.TradingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TradingInsertRequest {
    private Long stockId;
    private Long accountId;
    private TradingType tradingType;
    private Double price;
    private Double volume;
    private Double amount;
    private String memo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tradeTime;
}
