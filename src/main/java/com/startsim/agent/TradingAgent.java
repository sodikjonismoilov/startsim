package com.startsim.agent;

import com.startsim.market.MarketState;
import com.startsim.portfolio.Portfolio;

public interface TradingAgent {
    TradeDecision decide(MarketState state, Portfolio portfolio);
}
