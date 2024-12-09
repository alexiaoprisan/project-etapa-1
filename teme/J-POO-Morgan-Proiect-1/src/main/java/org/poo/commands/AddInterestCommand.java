package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Account;
import org.poo.account.SavingsAccount;
import org.poo.bankingApp.ExchangeRates;
import org.poo.bankingApp.UserRegistry;
import org.poo.transaction.*;
import org.poo.user.User;

public class AddInterestCommand implements Command {
    private UserRegistry userRegistry;
    private ArrayNode output;
    private int timestamp;
    private String accountIBAN;

    public AddInterestCommand(UserRegistry userRegistry, ArrayNode output, int timestamp, String accountIBAN) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.timestamp = timestamp;
        this.accountIBAN = accountIBAN;
    }

    @Override
    public void execute() {
        Account account = userRegistry.getAccountByIBAN(accountIBAN);
        if (account == null) {
            return;
        }

        if(account.getType().equals("savings")) {
            SavingsAccount savingsAccount = (SavingsAccount) account;
            double interestRate = savingsAccount.getInterestRate();
            double newSum = savingsAccount.getBalance() * interestRate + savingsAccount.getBalance();
            savingsAccount.setBalance(newSum);
        }
        else {
            ObjectNode node = output.addObject();
            node.put("command", "addInterest");
            ObjectNode outputNode = node.putObject("output");
            outputNode.put("description", "This is not a savings account");
            outputNode.put("timestamp", timestamp);
            node.put("timestamp", timestamp);
        }
    }

}
