package org.poo.exchangeRates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents a collection of exchange rates and provides utilities
 * to add, find, and convert exchange rates between currencies.
 */
public final class ExchangeRates {

    private final ArrayList<ExchangeInputFormat> exchangeRates = new ArrayList<>();

    /**
     * Adds a new exchange rate.
     *
     * @param exchangeInput the exchange rate to add
     */
    public void addExchangeRate(final ExchangeInputFormat exchangeInput) {
        exchangeRates.add(exchangeInput);
    }

    /**
     * Returns the list of exchange rates.
     *
     * @return the list of exchange rates
     */
    public ArrayList<ExchangeInputFormat> getExchangeRates() {
        return new ArrayList<>(exchangeRates);
    }

    /**
     * Adds a new exchange rate to the list.
     *
     * @param exchangeInput the exchange rate to add
     */
    public void addExchangeRates(final ExchangeInputFormat exchangeInput) {
        exchangeRates.add(exchangeInput);
    }

    /**
     * Finds and adds reciprocal exchange rates to the collection.
     */
    public void findNewExchangeRates() {
        ArrayList<ExchangeInputFormat> newRates = new ArrayList<>();

        for (ExchangeInputFormat exchange : exchangeRates) {
            if (exchange.getRate() == 0) {
                continue;
            }
            double amount = 1.0 / exchange.getRate();
            ExchangeInputFormat newExchange = new ExchangeInputFormat(
                    exchange.getTo(), exchange.getFrom(), amount, exchange.getTimestamp());
            newRates.add(newExchange);
        }

        exchangeRates.addAll(newRates);
    }

    /**
     * Converts an exchange rate between two currencies using a graph-based approach.
     *
     * @param currencyFrom the source currency
     * @param currencyTo   the target currency
     * @return the converted exchange rate, or 0 if not available
     */
    public double convertExchangeRate(final String currencyFrom, final String currencyTo) {
        Map<String, List<ExchangeInputFormat>> graph = new HashMap<>();
        for (ExchangeInputFormat exchange : exchangeRates) {
            graph.computeIfAbsent(exchange.getFrom(), k -> new ArrayList<>()).add(exchange);
        }

        Queue<ExchangePath> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new ExchangePath(currencyFrom, 1.0));

        while (!queue.isEmpty()) {
            ExchangePath current = queue.poll();
            String currentCurrency = current.getCurrency();
            double currentRate = current.getRate();

            visited.add(currentCurrency);

            List<ExchangeInputFormat> neighbors = graph.getOrDefault(currentCurrency,
                    Collections.emptyList());
            for (ExchangeInputFormat neighbor : neighbors) {
                String nextCurrency = neighbor.getTo();
                double nextRate = currentRate * neighbor.getRate();

                if (nextCurrency.equals(currencyTo)) {
                    return nextRate;
                }

                if (!visited.contains(nextCurrency)) {
                    queue.add(new ExchangePath(nextCurrency, nextRate));
                }
            }
        }

        return 0;
    }

    /**
     * Represents a path in the currency exchange graph.
     */
    private static final class ExchangePath {
        private final String currency;
        private final double rate;

        /**
         * Constructs a new ExchangePath.
         *
         * @param currency the currency
         * @param rate     the exchange rate
         */
        ExchangePath(final String currency, final double rate) {
            this.currency = currency;
            this.rate = rate;
        }

        public String getCurrency() {
            return currency;
        }

        public double getRate() {
            return rate;
        }
    }
}
