package com.startsim.engine;

import com.startsim.agent.TradeAction;
import com.startsim.agent.TradeDecision;
import com.startsim.market.MarketState;
import com.startsim.portfolio.Portfolio;
import com.startsim.portfolio.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutionEngine {

    private static final double TRANSACTION_COST_RATE = 0.001; // 0.1%

    public void execute(TradeDecision decision, MarketState state, Portfolio portfolio) {
        if (decision.getAction() == TradeAction.HOLD || decision.getQuantity() <= 0) {
            return;
        }
        double price = state.getPrice();
        String ticker = decision.getTicker();
        int requestedQty = decision.getQuantity();

        if (decision.getAction() == TradeAction.BUY) {
            double grossCost = requestedQty * price;
            double fee = grossCost * TRANSACTION_COST_RATE;
            double totalCost = grossCost + fee;
            if (portfolio.getCash() < totalCost) {
                log.warn("Insufficient cash for BUY {} x {}: need {}, have {}",
                        ticker, requestedQty, totalCost, portfolio.getCash());
                return;
            }
            portfolio.addCash(-totalCost);
            portfolio.updatePosition(ticker, requestedQty, price);
            log.debug("Executed BUY {} x {} @ {}, fee={}", ticker, requestedQty, price, fee);
        } else {
            Position pos = portfolio.getPositions().get(ticker);
            int held = pos == null ? 0 : pos.getQuantity();
            int toSell = Math.min(requestedQty, held);
            if (toSell <= 0) {
                log.warn("No position to SELL for {}", ticker);
                return;
            }
            double grossProceeds = toSell * price;
            double fee = grossProceeds * TRANSACTION_COST_RATE;
            double netProceeds = grossProceeds - fee;
            portfolio.updatePosition(ticker, -toSell, price);
            portfolio.addCash(netProceeds);
            log.debug("Executed SELL {} x {} @ {}, fee={}", ticker, toSell, price, fee);
        }
    }
}
