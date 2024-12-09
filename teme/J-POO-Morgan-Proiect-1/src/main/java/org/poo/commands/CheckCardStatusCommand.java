package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Account;
import org.poo.bankingApp.UserRegistry;
import org.poo.card.Card;
import org.poo.transaction.Transaction;
import org.poo.transaction.WarningForPay;
import org.poo.user.User;

public class CheckCardStatusCommand implements Command {
    private final UserRegistry userRegistry;
    private final ArrayNode Output;
    private final String cardNumber;
    private final int timestamp;

    public CheckCardStatusCommand(UserRegistry userRegistry, ArrayNode output, String cardNumber, int timestamp) {
        this.userRegistry = userRegistry;
        this.Output = output;
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;

    }

    @Override
    public void execute() {
        Card card = userRegistry.getCardByNumber(cardNumber);
        if (card == null) {

            ObjectNode output = Output.addObject();
            output.put("command", "checkCardStatus");
            ObjectNode outputNode = output.putObject("output");
            outputNode.put("description", "Card not found");
            outputNode.put("timestamp", timestamp);

            output.put("timestamp", timestamp);
            return;
        }

        User user = userRegistry.getUserByCardNumber(cardNumber);
        if (user == null) {
            return;
        }

        Account account = user.getAccountByCardNumber(cardNumber);
        if (account == null) {
            return;
        }



        if (account.getBalance() - account.getMinBalance() < 30) {
            Transaction transaction = new WarningForPay(timestamp, "You have reached the minimum amount of funds, the card will be frozen");
            user.addTransaction(transaction);
            card.setStatus("frozen");
            account.addTransaction(transaction);
        }


    }
}
