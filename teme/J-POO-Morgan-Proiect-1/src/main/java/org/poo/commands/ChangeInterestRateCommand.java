package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Account;
import org.poo.account.SavingsAccount;
import org.poo.bankingApp.ExchangeRates;
import org.poo.bankingApp.UserRegistry;
import org.poo.transaction.*;
import org.poo.user.User;

public class ChangeInterestRateCommand implements Command {
    private UserRegistry userRegistry;
    private ArrayNode output;
    private int timestamp;
    private String accountIBAN;
    private double interestRate;

    public ChangeInterestRateCommand(UserRegistry userRegistry, ArrayNode output, int timestamp, String accountIBAN, double interestRate) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.timestamp = timestamp;
        this.accountIBAN = accountIBAN;
        this.interestRate = interestRate;
    }

    @Override
    public void execute() {
        Account account = userRegistry.getAccountByIBAN(accountIBAN);
        if (account == null) {
            return;
        }

        if(account.getType().equals("savings")) {
            SavingsAccount savingsAccount = (SavingsAccount) account;
            savingsAccount.setInterestRate(interestRate);

            Transaction transaction = new InterestRateChange(timestamp, "Interest rate of the account changed to " + interestRate);
            User user = userRegistry.getUserByIBAN(accountIBAN);
            user.addTransaction(transaction);
        }
        else {
            ObjectNode node = output.addObject();
            node.put("command", "changeInterestRate");
            ObjectNode outputNode = node.putObject("output");
            outputNode.put("description", "This is not a savings account");
            outputNode.put("timestamp", timestamp);
            node.put("timestamp", timestamp);
        }
    }
}
