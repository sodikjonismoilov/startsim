package com.startsim.agent;

import com.startsim.market.MarketState;
import com.startsim.portfolio.Portfolio;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MomentumAgent implements TradingAgent {

    private static final String MA_KEY = "ma";
    private static final int FIXED_QUANTITY = 10;

    @Override
    public TradeDecision decide(MarketState state, Portfolio portfolio) {
        Double ma = state.getIndicators().get(MA_KEY);
        if (ma == null) {
            return hold(state.getTicker(), "no_ma");
        }
        double price = state.getPrice();
        List<String> reasons = new ArrayList<>();
        if (price > ma) {
            reasons.add("price_above_ma");
            return TradeDecision.builder()
                    .action(TradeAction.BUY)
                    .ticker(state.getTicker())
                    .quantity(FIXED_QUANTITY)
                    .confidence(0.8)
                    .reasonCodes(reasons)
                    .build();
        }
        if (price < ma) {
            reasons.add("price_below_ma");
            return TradeDecision.builder()
                    .action(TradeAction.SELL)
                    .ticker(state.getTicker())
                    .quantity(FIXED_QUANTITY)
                    .confidence(0.8)
                    .reasonCodes(reasons)
                    .build();
        }
        return hold(state.getTicker(), "price_equals_ma");
    }

    private static TradeDecision hold(String ticker, String reason) {
        return TradeDecision.builder()
                .action(TradeAction.HOLD)
                .ticker(ticker)
                .quantity(0)
                .confidence(0.0)
                .reasonCodes(List.of(reason))
                .build();
    }
}
