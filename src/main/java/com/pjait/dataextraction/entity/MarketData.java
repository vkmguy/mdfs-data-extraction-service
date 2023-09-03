package com.pjait.dataextraction.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
@Table(name = "market_data")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("Date")
    private Calendar date;
    @JsonProperty("Open")
    private BigDecimal open;
    @JsonProperty("Low")
    private BigDecimal low;
    @JsonProperty("High")
    private BigDecimal high;
    @JsonProperty("Close")
    private BigDecimal close;
    @JsonProperty("Volume")
    private Long volume;
    @JsonProperty("Dividends")
    private Long dividends;
    @JsonProperty("Stock Splits")
    private Long stock_splits;
}

