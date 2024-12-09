package org.poo.commands;

import org.poo.account.Account;
import org.poo.bankingApp.UserRegistry;
import org.poo.transaction.NewAccountCreated;
import org.poo.transaction.Transaction;
import org.poo.user.User;
import org.poo.card.Card;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.utils.Utils;

public class AddAccountCommand implements Command {
    private final UserRegistry userRegistry;
    private final ArrayNode output;
    private final int timestamp;
    private final String email;
    private final String accountType;
    private final String currency;

    public AddAccountCommand(UserRegistry userRegistry, ArrayNode output, int timestamp, String email, String accountType, String currency) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.timestamp = timestamp;
        this.email = email;
        this.accountType = accountType;
        this.currency = currency;
    }

    @Override
    public void execute() {
        User user = userRegistry.getUserByEmail(email);
        String IBAN = Utils.generateIBAN();
        user.addAccount(accountType, currency, IBAN);

        Transaction transaction = new NewAccountCreated(timestamp, "New account created");
        user.addTransaction(transaction);

        Account account = user.getAccountByIBAN(IBAN);

        account.addTransaction(transaction);

    }
}
