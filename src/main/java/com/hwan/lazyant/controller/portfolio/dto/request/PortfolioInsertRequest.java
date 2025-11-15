package com.hwan.lazyant.controller.portfolio.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PortfolioInsertRequest {
    private String name;
    private List<PortfolioItemInsertRequest> items;
}
