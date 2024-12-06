package org.poo.commands;

import org.poo.account.Account;
import org.poo.bankingApp.UserRegistry;
import org.poo.transaction.NewAccountCreated;
import org.poo.transaction.NewCardCreatedTransaction;
import org.poo.transaction.Transaction;
import org.poo.user.User;
import org.poo.card.Card;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.utils.Utils;

public class CreateCardCommand implements Command {

    private final UserRegistry userRegistry;
    private final ArrayNode output;
    private final int timestamp;
    private final String email;
    private final String IBAN;
    private final String command;

    public CreateCardCommand(UserRegistry userRegistry, ArrayNode output, int timestamp, String email, String IBAN, String command) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.timestamp = timestamp;
        this.email = email;
        this.IBAN = IBAN;
        this.command = command;
    }

    @Override
    public void execute() {
        User user = userRegistry.getUserByEmail(email);

        if (user == null) {
            return;
        }

        String type;

        if (command.equals("createCard")) {
            type = "regular";
        }
        else {
            type = "oneTimePay";
        }

        String cardNumber = Utils.generateCardNumber();

        for (Account acc : user.getAccounts()) {
            if (acc.getIBAN().equals(IBAN)) {
                acc.createCard(type, cardNumber);
            }
        }

        Transaction transaction = new NewCardCreatedTransaction(timestamp, "New card created", IBAN, cardNumber, email);
        user.addTransaction(transaction);
    }
}
