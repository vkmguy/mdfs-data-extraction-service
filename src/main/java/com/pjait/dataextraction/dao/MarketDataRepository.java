package com.pjait.dataextraction.dao;

import com.pjait.dataextraction.entity.MarketData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketDataRepository extends JpaRepository<MarketData, Long> {

}
