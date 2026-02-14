package com.startsim.simulation;

import com.startsim.agent.TradeDecision;
import com.startsim.agent.TradingAgent;
import com.startsim.engine.ExecutionEngine;
import com.startsim.market.MarketState;
import com.startsim.portfolio.Portfolio;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SimulationRunner {

    private final ExecutionEngine executionEngine = new ExecutionEngine();

    public Portfolio run(List<MarketState> states, TradingAgent agent, Portfolio initialPortfolio) {
        Portfolio portfolio = initialPortfolio;
        for (MarketState state : states) {
            TradeDecision decision = agent.decide(state, portfolio);
            executionEngine.execute(decision, state, portfolio);
        }
        return portfolio;
    }
}
