package com.pjait.dataextraction.service;

import com.pjait.dataextraction.dao.MarketDataRepository;
import com.pjait.dataextraction.entity.MarketData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketDataService {
    private MarketDataRepository repository;

    @Autowired
    public void setRepository(MarketDataRepository repository) {
        this.repository = repository;
    }

    public void saveMarketData(MarketData marketData) {
        repository.save(marketData);
    }
}
