package com.hwan.lazyant.service.trading;

import com.hwan.lazyant.controller.trading.dto.TradingInsertRequest;
import com.hwan.lazyant.mapper.TradingMapper;
import com.hwan.lazyant.model.Trading;
import com.hwan.lazyant.repository.trading.TradingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TradingService {

    private final TradingRepository tradingRepository;

    @Transactional
    public void insert(TradingInsertRequest request) {
        Trading newTrading = TradingMapper.mapToTrading(request);
        if(!newTrading.hasVolume()) {
            newTrading.calculateVolume(request.getTradePrice());
        }
        tradingRepository.save(newTrading);
    }
}
