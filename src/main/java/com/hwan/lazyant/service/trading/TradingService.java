package com.hwan.lazyant.service.trading;

import com.hwan.lazyant.controller.trading.dto.TradingInsertRequest;
import com.hwan.lazyant.mapper.trading.TradingMapper;
import com.hwan.lazyant.model.trading.Trading;
import com.hwan.lazyant.repository.trading.TradingRepository;
import com.hwan.lazyant.service.asset.StockAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TradingService {

    private final TradingRepository tradingRepository;
    private final StockAssetService stockAssetService;

    @Transactional
    public Trading write(TradingInsertRequest request) {
        Trading newTrading = TradingMapper.mapToTrading(request);

        if(!newTrading.hasVolume() && newTrading.hasPrice()) {
            newTrading.calculateVolume(request.getAmount());
        } else if(newTrading.hasVolume() && !newTrading.hasPrice()) {
            newTrading.calculatePrice(request.getAmount());
        } else if(!newTrading.hasVolume() && !newTrading.hasPrice()) {
            throw new IllegalArgumentException("Cannot write tradingHistory. Both volume and marketCurrentPrice is null");
        }

        tradingRepository.save(newTrading);
        stockAssetService.accumulate(newTrading);

        return newTrading;
    }
}
