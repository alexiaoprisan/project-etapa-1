package org.poo.commands;

import org.poo.account.Account;
import org.poo.bankingApp.UserRegistry;
import org.poo.transaction.CardDestroyed;
import org.poo.transaction.Transaction;
import org.poo.user.User;
import org.poo.card.Card;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DeleteCardCommand implements Command {
    private final UserRegistry userRegistry;
    private final ArrayNode output;
    private final int timestamp;
    private final String email;
    private final String cardNumber;

    public DeleteCardCommand(UserRegistry userRegistry, ArrayNode output, int timestamp, String email, String cardNumber) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.timestamp = timestamp;
        this.email = email;
        this.cardNumber = cardNumber;
    }

    @Override
    public void execute() {
        User user = userRegistry.getUserByEmail(email);

        if (user == null) {
            return;
        }

        for (Account account : user.getAccounts()) {
            Card card = account.getCardByNumber(cardNumber);
            if (card != null) {
                account.getCards().remove(card);
                Transaction transaction = new CardDestroyed(timestamp, "The card has been destroyed", account.getIBAN(), cardNumber, user.getEmail());
                user.addTransaction(transaction);
                return;
            }
        }
    }


}
