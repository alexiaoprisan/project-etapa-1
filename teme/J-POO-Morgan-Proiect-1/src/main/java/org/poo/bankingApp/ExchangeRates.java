package org.poo.bankingApp;

import java.util.ArrayList;


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
        // Direct exchange rate
        for (ExchangeInputFormat exchange : exchangeRates) {
            if (exchange.getFrom().equals(currencyFrom) && exchange.getTo().equals(currencyTo)) {
                return exchange.getRate();
            }
        }

        // Reverse exchange rate
        for (ExchangeInputFormat exchange : exchangeRates) {
            if (exchange.getFrom().equals(currencyTo) && exchange.getTo().equals(currencyFrom)) {
                ExchangeInputFormat reverseExchange = new ExchangeInputFormat(currencyTo, currencyFrom, 1.0 / exchange.getRate(), exchange.getTimestamp());
                exchangeRates.add(reverseExchange);

                return 1.0 / exchange.getRate();
            }
        }

        // Two-step conversion
        for (ExchangeInputFormat firstExchange : exchangeRates) {
            if (firstExchange.getFrom().equals(currencyFrom)) {
                for (ExchangeInputFormat secondExchange : exchangeRates) {
                    if (secondExchange.getFrom().equals(firstExchange.getTo()) && secondExchange.getTo().equals(currencyTo)) {
                        // add the new exchange rate in
                        ExchangeInputFormat newExchange = new ExchangeInputFormat(currencyFrom, currencyTo, firstExchange.getRate() * secondExchange.getRate(), firstExchange.getTimestamp());
                        exchangeRates.add(newExchange);
                        return firstExchange.getRate() * secondExchange.getRate();
                    }
                }
            }
        }

        // No conversion rate available
        return 0;
    }


}
