package com.startsim.portfolio;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Position {
    String ticker;
    int quantity;
    double avgPrice;
}
