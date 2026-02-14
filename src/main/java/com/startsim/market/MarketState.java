package com.startsim.market;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;

@Value
@Builder
public class MarketState {
    String ticker;
    LocalDateTime timestamp;
    double price;
    @Builder.Default
    Map<String, Double> indicators = Map.of();
}
