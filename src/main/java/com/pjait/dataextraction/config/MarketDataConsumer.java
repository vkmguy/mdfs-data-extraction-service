package com.pjait.dataextraction.config;

import com.pjait.dataextraction.entity.MarketData;
import com.pjait.dataextraction.service.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Service
public class MarketDataConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketDataConsumer.class);

    private MarketDataService marketDataService;

    @Autowired
    public void setDataRepository(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @KafkaListener(topics = "market-db-data-topic", groupId = "market-data", containerFactory = "marketDataListener")
    public void consume(MarketData marketData) {
            marketDataService.saveMarketData(marketData);
    }
}

