package com.hwan.lazyant.controller.portfolio.dto;

import com.hwan.lazyant.model.portfolio.Factor;
import lombok.Getter;

@Getter
public class PortfolioItemInsertRequest {
    private Factor factor;
    private int weight;
}
