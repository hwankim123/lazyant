package com.hwan.lazyant.controller.trading;

import com.hwan.lazyant.controller.trading.dto.TradingInsertRequest;
import com.hwan.lazyant.service.trading.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tradings")
public class TradingController {

    private final TradingService tradingService;

    @PostMapping
    public void insert(@RequestBody TradingInsertRequest request) {
        tradingService.write(request);
    }
}
