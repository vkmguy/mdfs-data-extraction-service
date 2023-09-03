package com.pjait.dataextraction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjait.dataextraction.entity.MarketData;
import com.pjait.dataextraction.service.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
public class DataExtractionController {

    private static final String POST_URI = "/api/historical-data";

    private KafkaProducerService kafkaProducerService;

    @Value("${yahoo.market.symbol}")
    private String marketSymbol;

    @Value("${yahoo.market.fetch.days}")
    private Integer days;

    @Value(("${finance.api.host}"))
    private String host;

    private RestTemplate restTemplate;

    @Autowired
    public void setKafkaProducerService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/fetchHistoricalData/{marketSymbol}/{startDate}/{endDate}")
    @ResponseBody
    public ResponseEntity<String> fetchDataDateRange(@PathVariable("marketSymbol") String marketSymbol, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws JsonProcessingException {
        // Fetch data from Yahoo Finance using yFinance API
        String postUrl = "/" + marketSymbol + "/" + "date-range" + "/" + startDate + "/" + endDate;
        int numberOfRecords = extractData(postUrl);
        return ResponseEntity.ok(String.format("Date Range Data extraction initiated for %d records", numberOfRecords));
    }

    @GetMapping("/fetchHistoricalData/{marketSymbol}")
    @ResponseBody
    public ResponseEntity<String> fetchHistoricalData(@PathVariable("marketSymbol") String marketSymbol) throws JsonProcessingException {
        // Fetch data from Yahoo Finance from YFinance API
        String postUrl = "/" + marketSymbol;
        int numberOfRecords = extractData(postUrl);
        return ResponseEntity.ok(String.format("Historical Data extraction initiated for %d records", numberOfRecords));
    }

    private int extractData(String postUrl) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(host + POST_URI + postUrl, String.class);

        ObjectMapper mapper = new ObjectMapper();
        MarketData[] historicalDataArray = mapper.readValue(response.getBody(), MarketData[].class);
        List<MarketData> historicalDataList = Arrays.asList(Objects.requireNonNull(historicalDataArray));

        historicalDataList.forEach(data -> kafkaProducerService.sendMessage(data));
        return historicalDataList.size();
    }
}