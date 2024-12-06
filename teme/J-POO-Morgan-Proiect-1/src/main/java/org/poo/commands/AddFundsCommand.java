package org.poo.commands;

import org.poo.account.Account;
import org.poo.bankingApp.UserRegistry;
import org.poo.user.User;
import org.poo.card.Card;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AddFundsCommand implements Command {
    private final UserRegistry userRegistry;
    private final ArrayNode output;
    private final int timestamp;
    private final String IBAN;
    private final double amount;

    public AddFundsCommand(UserRegistry userRegistry, ArrayNode output, int timestamp, String IBAN, double amount) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.timestamp = timestamp;
        this.IBAN = IBAN;
        this.amount = amount;
    }

    @Override
    public void execute() {
        for (User user : userRegistry.getUsers()) {
            Account account = user.getAccountByIBAN(IBAN);
            if (account != null) {
                account.setBalance(account.getBalance() + amount);
                return;
            }
        }
    }

}
