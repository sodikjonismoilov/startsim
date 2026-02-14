package com.startsim.agent;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class TradeDecision {
    TradeAction action;
    String ticker;
    int quantity;
    double confidence;
    @Builder.Default
    List<String> reasonCodes = List.of();
}
