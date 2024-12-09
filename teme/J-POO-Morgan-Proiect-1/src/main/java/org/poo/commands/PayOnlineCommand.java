package org.poo.commands;

import org.poo.account.Account;
import org.poo.account.ClassicAccount;
import org.poo.bankingApp.UserRegistry;
import org.poo.card.OneTimePayCard;
import org.poo.commerciants.Commerciant;
import org.poo.report.ClassicReport;
import org.poo.report.PaymentsRecord;
import org.poo.transaction.*;
import org.poo.user.User;
import org.poo.card.Card;
import org.poo.bankingApp.ExchangeRates;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.utils.Utils;

public class PayOnlineCommand implements Command {
    private final UserRegistry userRegistry;
    public final ExchangeRates exchangeRates;
    private final ArrayNode output;
    private final int timestamp;
    private final String cardNumber;
    private double amount;
    private final String currency;
    private final String description;
    private final String commerciant;
    private final String email;

    public PayOnlineCommand(UserRegistry userRegistry, ExchangeRates exchangeRates, ArrayNode output, int timestamp,
                            String cardNumber, double amount, String currency,
                            String description, String commerciant, String email) {
        this.userRegistry = userRegistry;
        this.exchangeRates = exchangeRates;
        this.output = output;
        this.timestamp = timestamp;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.commerciant = commerciant;
        this.email = email;
    }

    @Override
    public void execute() {
        User user = userRegistry.getUserByEmail(email);

        double copyAmount = amount;

        if (user == null) {
            return;
        }

        int foundCard = 0;

        for (Account account : user.getAccounts()) {
            Card card = account.getCardByNumber(cardNumber);

            if (card != null) {
                foundCard = 1;

                String cardCurrency = account.getCurrency();

                if (!cardCurrency.equals(currency)) {
                    double rate = exchangeRates.convertExchangeRate(cardCurrency, currency);
                    if (rate != 0) {
                        amount = amount / rate;
                    }
                }

                if (card.getStatus().equals("frozen")) {
                    Transaction transaction = new FrozenCard(timestamp, "The card is frozen");
                    user.addTransaction(transaction);
                    return;
                }

                if (account.getBalance() >= amount) {

                    if (account.getBalance() - amount <= account.getMinBalance()) {
                        card.setStatus("frozen");
                        Transaction transactionErorr = new WarningForPay(timestamp, "You have reached the minimum amount of funds, the card will be frozen");
                        user.addTransaction(transactionErorr);
                        return;
                    }

                    if (account.getBalance() - account.getMinBalance() - amount < 30) {
                        Transaction transactionErorr = new WarningForPay(timestamp, "You have reached the minimum amount of funds, the card will be frozen");
                        user.addTransaction(transactionErorr);
                        card.setStatus("warning");
                        return;
                    }

                    account.setBalance(account.getBalance() - amount);

                    Transaction transaction = new CardPaymentTransaction(timestamp, "Card payment", amount, commerciant);
                    user.addTransaction(transaction);

                    if (account.getType().equals("classic")) {
                        Commerciant newCommerciant = new Commerciant(commerciant, amount, timestamp);

                        ClassicAccount classicAccount = (ClassicAccount) account;
                        ClassicReport report = classicAccount.getReport();
                        PaymentsRecord paymentsRecord = classicAccount.getPaymentsRecord();

                        report.addTransaction(transaction);
                        paymentsRecord.addTransaction(transaction);

                        classicAccount.addCommerciant(newCommerciant);
                    }

                    if (card.getType().equals("oneTimePay")) {
                        String newCardNumber = Utils.generateCardNumber();
                        card.setCardNumber(newCardNumber);
                    }

                } else {
                    Transaction transaction = new InsufficientFunds(timestamp, "Insufficient funds");
                    user.addTransaction(transaction);
                }

            }
        }

        if (foundCard == 0) {
            ObjectNode outputNode = output.addObject();
            outputNode.put("command", "payOnline");
            ObjectNode outputObject = outputNode.putObject("output");
            outputObject.put("description", "Card not found");
            outputObject.put("timestamp", timestamp);
            outputNode.put("timestamp", timestamp);
        }

    }
}
