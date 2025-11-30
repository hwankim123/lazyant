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
    public Trading insert(TradingInsertRequest request) {
        Trading trading = TradingMapper.mapToTrading(request);

        if(!trading.hasQuantity() && trading.hasPrice()) {
            trading.calculateQuantity(request.getAmount());
        } else if(trading.hasQuantity() && !trading.hasPrice()) {
            trading.calculatePrice(request.getAmount());
        } else if(!trading.hasQuantity() && !trading.hasPrice()) {
            throw new IllegalArgumentException("Cannot write tradingHistory. Both volume and marketCurrentPrice is null");
        }

        tradingRepository.save(trading);
        stockAssetService.accumulate(trading);

        return trading;
    }
}
