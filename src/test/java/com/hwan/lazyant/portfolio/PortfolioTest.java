package com.hwan.lazyant.portfolio;

import com.hwan.lazyant.model.portfolio.Factor;
import com.hwan.lazyant.model.portfolio.Portfolio;
import com.hwan.lazyant.model.portfolio.PortfolioItem;

import java.util.ArrayList;

public class PortfolioTest {

    public static Portfolio createIndexOnlyPortfolio() {
        ArrayList<PortfolioItem> items = new ArrayList<>();
        items.add(new PortfolioItem(Factor.INDEX, 100));
        return Portfolio.withUnknownFactor("name", items);
    }
}
