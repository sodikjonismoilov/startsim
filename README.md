# StartSim

Paper-trading simulation platform with autonomous agents.

## Run the simulation

```bash
./gradlew bootRun
```

The main method runs a local simulation and prints the final portfolio value (cash, positions, total equity).

## Structure

- **com.startsim.market** – `MarketState` (ticker, timestamp, price, indicators)
- **com.startsim.portfolio** – `Position`, `Portfolio` (cash, positions, getTotalEquity, updatePosition)
- **com.startsim.agent** – `TradeAction`, `TradeDecision`, `TradingAgent`, `MomentumAgent` (MA-based buy/sell/hold)
- **com.startsim.engine** – `ExecutionEngine` (execute with 0.1% fee, no negative cash/position)
- **com.startsim.simulation** – `SimulationRunner` (runs over market states, returns final portfolio)

No database, REST, or async in the core simulation—logic only.
