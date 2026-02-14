package com.startsim.startsim;

import com.startsim.agent.MomentumAgent;
import com.startsim.agent.TradingAgent;
import com.startsim.market.MarketState;
import com.startsim.portfolio.Portfolio;
import com.startsim.simulation.SimulationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class StartsimApplication {

    private static final Logger log = LoggerFactory.getLogger(StartsimApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StartsimApplication.class, args);
        runSimulation();
    }

    private static void runSimulation() {
        List<MarketState> states = List.of(
                MarketState.builder()
                        .ticker("AAPL")
                        .timestamp(LocalDateTime.now().minusDays(5))
                        .price(150.0)
                        .indicators(Map.of("ma", 148.0))
                        .build(),
                MarketState.builder()
                        .ticker("AAPL")
                        .timestamp(LocalDateTime.now().minusDays(4))
                        .price(152.0)
                        .indicators(Map.of("ma", 149.0))
                        .build(),
                MarketState.builder()
                        .ticker("AAPL")
                        .timestamp(LocalDateTime.now().minusDays(3))
                        .price(148.0)
                        .indicators(Map.of("ma", 150.0))
                        .build(),
                MarketState.builder()
                        .ticker("AAPL")
                        .timestamp(LocalDateTime.now().minusDays(2))
                        .price(155.0)
                        .indicators(Map.of("ma", 151.0))
                        .build(),
                MarketState.builder()
                        .ticker("AAPL")
                        .timestamp(LocalDateTime.now().minusDays(1))
                        .price(153.0)
                        .indicators(Map.of("ma", 152.0))
                        .build()
        );

        Portfolio initial = Portfolio.builder().cash(100_000.0).build();
        TradingAgent agent = new MomentumAgent();
        SimulationRunner runner = new SimulationRunner();

        Portfolio finalPortfolio = runner.run(states, agent, initial);
        double lastPrice = states.get(states.size() - 1).getPrice();
        double totalEquity = finalPortfolio.getTotalEquity(lastPrice);

        log.info("Simulation complete.");
        System.out.println("--- StartSim Simulation Result ---");
        System.out.printf("Final cash: $%.2f%n", finalPortfolio.getCash());
        System.out.printf("Positions: %s%n", finalPortfolio.getPositions());
        System.out.printf("Total equity (at last price %.2f): $%.2f%n", lastPrice, totalEquity);
        System.out.println("----------------------------------");
    }
}
