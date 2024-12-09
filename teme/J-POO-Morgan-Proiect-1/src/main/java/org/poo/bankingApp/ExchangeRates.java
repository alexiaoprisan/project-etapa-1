package org.poo.bankingApp;

import java.util.*;


public class ExchangeRates {
    ArrayList<ExchangeInputFormat> exchangeRates = new ArrayList<>();

    public void addExchangeRate(ExchangeInputFormat exchangeInput) {
        exchangeRates.add(exchangeInput);
    }

    public ArrayList<ExchangeInputFormat> getExchangeRates() {
        return exchangeRates;
    }

    public void addExchangeRates(ExchangeInputFormat exchangeInput) {
        exchangeRates.add(exchangeInput);
    }

    public void findNewExchangeRates() {
        ArrayList<ExchangeInputFormat> newRates = new ArrayList<>(); // Temporary list for new exchanges

        for (ExchangeInputFormat exchange : exchangeRates) {
            if (exchange.getRate() == 0) {
                continue;
            }
            double amount = 1.0 / exchange.getRate();
            ExchangeInputFormat newExchange = new ExchangeInputFormat(exchange.getTo(), exchange.getFrom(), amount, exchange.getTimestamp());
            newRates.add(newExchange); // Add new exchange to the temporary list
        }
        // Add all new exchange rates to the original list after the loop
        exchangeRates.addAll(newRates);
    }


    public double convertExchangeRate(String currencyFrom, String currencyTo) {
        // Build the graph
        Map<String, List<ExchangeInputFormat>> graph = new HashMap<>();
        for (ExchangeInputFormat exchange : exchangeRates) {
            graph.computeIfAbsent(exchange.getFrom(), k -> new ArrayList<>()).add(exchange);
        }

        // BFS to find the exchange rate
        Queue<ExchangePath> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new ExchangePath(currencyFrom, 1.0));

        while (!queue.isEmpty()) {
            ExchangePath current = queue.poll();
            String currentCurrency = current.currency;
            double currentRate = current.rate;

            // Mark as visited
            visited.add(currentCurrency);

            // Check neighbors
            List<ExchangeInputFormat> neighbors = graph.getOrDefault(currentCurrency, Collections.emptyList());
            for (ExchangeInputFormat neighbor : neighbors) {
                String nextCurrency = neighbor.getTo();
                double nextRate = currentRate * neighbor.getRate();

                // If target is found
                if (nextCurrency.equals(currencyTo)) {
                    return nextRate;
                }

                // Add to queue if not visited
                if (!visited.contains(nextCurrency)) {
                    queue.add(new ExchangePath(nextCurrency, nextRate));
                }
            }
        }

        // No conversion rate available
        return 0;
    }

    // Helper class for BFS
    private static class ExchangePath {
        String currency;
        double rate;

        ExchangePath(String currency, double rate) {
            this.currency = currency;
            this.rate = rate;
        }
    }



}
