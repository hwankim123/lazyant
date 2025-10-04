package com.hwan.lazyant.repository.trading;

import com.hwan.lazyant.model.trading.Trading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingRepository extends JpaRepository<Trading, Long> {
}
