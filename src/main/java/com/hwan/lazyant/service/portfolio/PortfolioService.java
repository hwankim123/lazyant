package com.hwan.lazyant.service.portfolio;

import com.hwan.lazyant.controller.portfolio.dto.PortfolioInsertRequest;
import com.hwan.lazyant.mapper.portfolio.PortfolioMapper;
import com.hwan.lazyant.model.portfolio.Portfolio;
import com.hwan.lazyant.repository.portfolio.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Transactional
    public void insert(PortfolioInsertRequest request) {
        Portfolio portfolio = PortfolioMapper.mapToPortfolio(request);
        portfolio.setUserId(1L);//TODO: 개인화
        portfolioRepository.save(portfolio);
    }

    public Portfolio findByUserId(long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Portfolio from repository. Given userId=%d".formatted(userId)));
    }
}
