package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Account;
import org.poo.bankingApp.UserRegistry;
import org.poo.user.User;

public class CommandsAccount {

    public void addAccount(UserRegistry userRegistry, ArrayNode Output, int timestamp, String email, String accountType, String currency) {
        User user = userRegistry.getUserByEmail(email);
        user.addAccount(accountType, currency);
    }

    public void addFunds(UserRegistry userRegistry, ArrayNode Output, int timestamp, String IBAN, double amount) {
        for (User user : userRegistry.getUsers()) {
            Account account = user.getAccountByIBAN(IBAN);
            if (account != null) {
                account.setBalance(account.getBalance() + amount);
                return;
            }
        }
    }

    public void deleteAccount(UserRegistry userRegistry, ArrayNode Output, int timestamp, String email, String IBAN) {
        User user = userRegistry.getUserByEmail(email);

        if (user == null) {
            return;
        }

        Account account = user.getAccountByIBAN(IBAN);
        if (account == null) {
            return;
        }

        if (account.getBalance() > 0) {
            return;
        }
        else {
            user.getAccounts().remove(account);

            ObjectNode output = Output.addObject();
            output.put("command", "deleteAccount");
            ObjectNode outputNode = output.putObject("output");
            outputNode.put("success", "Account deleted");
            outputNode.put("timestamp", timestamp);

            output.put("timestamp", timestamp);
            return;
        }
    }
}
