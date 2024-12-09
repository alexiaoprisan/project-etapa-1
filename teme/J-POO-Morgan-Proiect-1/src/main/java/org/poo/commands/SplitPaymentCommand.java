package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.account.Account;
import org.poo.exchangeRates.ExchangeRates;
import org.poo.user.UserRegistry;
import org.poo.transaction.*;
import org.poo.user.User;

import java.util.List;

public class SplitPaymentCommand implements  Command {
    private UserRegistry userRegistry;
    private ArrayNode output;
    private int timestamp;
    private double totalAmount;
    private String currency;
    private List<String> accountsIBAN;
    private ExchangeRates exchangeRates;


    public SplitPaymentCommand(UserRegistry userRegistry, ArrayNode output, int timestamp, double totalAmount,
                               String currency, List<String> accounts, ExchangeRates exchangeRates) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.timestamp = timestamp;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.accountsIBAN = accounts;
        this.exchangeRates = exchangeRates;
    }

    @Override
    public void execute() {
        double amount = totalAmount / accountsIBAN.size();

        int canDoSplit = 1;
        String poorAccount = "";

        // Validate all accounts exist and check if the split payment is possible
        for (String accountIBAN : accountsIBAN) {
            Account account = userRegistry.getAccountByIBAN(accountIBAN);
            if (account == null) {
                return; // Exit if any account does not exist
            }

            double requiredAmount = amount;
            if (!account.getCurrency().equals(currency)) {
                double exchangeRate = exchangeRates.convertExchangeRate(account.getCurrency(), currency);
                if (exchangeRate == 0) {
                    return; // Exit if exchange rate is invalid
                }
                requiredAmount = amount / exchangeRate;
            }

            if (account.getBalance() < requiredAmount) {
                poorAccount = accountIBAN;
                canDoSplit = 0;
            }
        }

        if (canDoSplit == 0) {

            for (String accountIBAN : accountsIBAN) {

                User user = userRegistry.getUserByIBAN(accountIBAN);

                Transaction transaction = new ErrorSplitPaymentTransaction(
                        timestamp, String.format("Split payment of %.2f %s", totalAmount, currency),
                        amount, currency, accountsIBAN.toArray(new String[0]), "Account " + poorAccount + " has insufficient funds for a split payment."
                );
                user.addTransaction(transaction);
                Account account = userRegistry.getAccountByIBAN(accountIBAN);
                account.addTransaction(transaction);
            }

            return; // Exit if any account does not have enough balance
        }

        // Deduct the amounts and log transactions
        for (String accountIBAN : accountsIBAN) {
            Account account = userRegistry.getAccountByIBAN(accountIBAN);

            double deductionAmount = amount;
            if (!account.getCurrency().equals(currency)) {
                double exchangeRate = exchangeRates.convertExchangeRate(account.getCurrency(), currency);
                deductionAmount = amount / exchangeRate;
            }

            account.setBalance(account.getBalance() - deductionAmount);

            // Add transaction to the user
            User user = userRegistry.getUserByIBAN(account.getIBAN());
            String description = String.format("Split payment of %.2f %s", totalAmount, currency);
            Transaction transaction = new SplitPaymentTransaction(
                    timestamp, description, amount, currency, accountsIBAN.toArray(new String[0])
            );
            user.addTransaction(transaction);
        }
    }


}
