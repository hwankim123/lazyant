package com.hwan.lazyant.repository.trading;

import com.hwan.lazyant.model.trading.Trading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradingRepository extends JpaRepository<Trading, Long> {

    @Query(value = """
            SELECT t FROM Trading t
            INNER JOIN Account a ON t.accountId = a.id
            WHERE a.userId = :userId and t.stockId = :stockId
            ORDER BY t.tradeAt DESC
            LIMIT 20
            """)
    List<Trading> findRecentTradings(@Param("userId") long userId, @Param("stockId") Long stockId);
}
