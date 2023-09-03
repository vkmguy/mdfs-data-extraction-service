package com.pjait.dataextraction.helper;

import com.pjait.dataextraction.entity.MarketData;

import java.util.List;

public class MarketDateListWrapper {

    private List<MarketData> marketDataList;

    public List<MarketData> getMarketDataList() {
        return marketDataList;
    }

    public void setMarketDataList(List<MarketData> marketDataList) {
        this.marketDataList = marketDataList;
    }
}
