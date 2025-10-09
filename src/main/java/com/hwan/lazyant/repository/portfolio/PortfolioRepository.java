package com.hwan.lazyant.repository.portfolio;

import com.hwan.lazyant.model.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByUserId(long userId);
}
