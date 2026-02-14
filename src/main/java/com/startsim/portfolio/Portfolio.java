package com.startsim.portfolio;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@Slf4j
public class Portfolio {
    @Builder.Default
    private double cash = 0.0;
    @Builder.Default
    private final Map<String, Position> positions = new HashMap<>();

    public void addCash(double amount) {
        this.cash += amount;
    }

    public double getTotalEquity(double currentPrice) {
        double positionsValue = positions.values().stream()
                .mapToDouble(p -> p.getQuantity() * currentPrice)
                .sum();
        return cash + positionsValue;
    }

    public void updatePosition(String ticker, int quantity, double price) {
        Position existing = positions.get(ticker);
        if (existing == null) {
            if (quantity > 0) {
                positions.put(ticker, Position.builder()
                        .ticker(ticker)
                        .quantity(quantity)
                        .avgPrice(price)
                        .build());
            }
            return;
        }
        int newQuantity = existing.getQuantity() + quantity;
        if (newQuantity <= 0) {
            positions.remove(ticker);
            return;
        }
        double newAvgPrice = quantity > 0
                ? (existing.getAvgPrice() * existing.getQuantity() + price * quantity) / newQuantity
                : existing.getAvgPrice();
        positions.put(ticker, Position.builder()
                .ticker(ticker)
                .quantity(newQuantity)
                .avgPrice(newAvgPrice)
                .build());
    }
}
