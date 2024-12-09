package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.account.Account;
import org.poo.bankingApp.ExchangeRates;
import org.poo.bankingApp.UserRegistry;
import org.poo.transaction.*;
import org.poo.user.User;

public class SendMoneyCommand implements Command {
    private final UserRegistry userRegistry;
    private final ArrayNode output;
    private final int timestamp;
    private double amount;
    private final String description;
    private final String email;
    private final String giverIBAN;
    private final String receiverIBAN;
    private final ExchangeRates exchangeRates;

    public SendMoneyCommand(UserRegistry userRegistry, ArrayNode output, int timestamp,
                            String giver, String receiver, double amount, String email, String description,
                            ExchangeRates exchangeRates) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.timestamp = timestamp;
        this.amount = amount;
        this.description = description;
        this.email = email;
        this.giverIBAN = giver;
        this.receiverIBAN = receiver;
        this.exchangeRates = exchangeRates;
    }

    @Override
    public void execute() {

        User giver = userRegistry.getUserByEmail(email);
        if (giver == null) {
            return;
        }

        Account giverAccount = giver.getAccountByIBAN(giverIBAN);

        if (giverAccount == null) {
            giverAccount = giver.getAccountByAlias(giverIBAN);
        }

        if (giverAccount == null) {
            return;
        }

        Account receiverAccount = userRegistry.getAccountByIBAN(receiverIBAN);

        User receiver = userRegistry.getUserByIBAN(receiverIBAN);


        if (receiverAccount == null) {

            receiverAccount = userRegistry.getAccountByAlias(receiverIBAN);
        }

        if (receiverAccount == null) {
            return;
        }


        String currencyFrom = giverAccount.getCurrency();
        String currencyTo = receiverAccount.getCurrency();

        if (currencyFrom.equals(currencyTo)) {
            if (giverAccount.getBalance() < amount) {
                return;
            }

            giverAccount.setBalance(giverAccount.getBalance() - amount);
            receiverAccount.setBalance(receiverAccount.getBalance() + amount);
            Transaction transaction = new SendMoneyTransaction(timestamp, description, giverIBAN, receiverIBAN, amount, currencyFrom, "sent");
            Transaction receiverTransaction = new SendMoneyTransaction(timestamp, description, giverIBAN, receiverIBAN, amount, currencyTo, "received");
            giver.addTransaction(transaction);
            receiver.addTransaction(receiverTransaction);
            return;
        }

        double exchangeRate = exchangeRates.convertExchangeRate(currencyFrom, currencyTo);
        double amountToTransfer = amount * exchangeRate;


        if (giverAccount.getBalance() < amount) {
            Transaction transaction = new InsufficientFunds(timestamp, "Insufficient funds");
            giver.addTransaction(transaction);
            return;
        }

        giverAccount.setBalance(giverAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amountToTransfer);


        Transaction transaction = new SendMoneyTransaction(timestamp, description, giverIBAN, receiverIBAN, amount, currencyFrom, "sent");
        Transaction receiverTransaction = new SendMoneyTransaction(timestamp, description, giverIBAN, receiverIBAN, amountToTransfer, currencyTo, "received");
        giver.addTransaction(transaction);
        receiver.addTransaction(receiverTransaction);

        Account account = userRegistry.getAccountByIBAN(giverIBAN);
        account.addTransaction(transaction);

    }
}
