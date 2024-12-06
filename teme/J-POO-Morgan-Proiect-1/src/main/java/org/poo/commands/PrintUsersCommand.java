package org.poo.commands;

import org.poo.account.Account;
import org.poo.bankingApp.UserRegistry;
import org.poo.user.User;
import org.poo.card.Card;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PrintUsersCommand implements Command {

    private final UserRegistry userRegistry;
    private final ArrayNode Output;
    private final int timestamp;

    public PrintUsersCommand(UserRegistry userRegistry, ArrayNode output, int timestamp) {
        this.userRegistry = userRegistry;
        this.Output = output;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        ObjectNode output = Output.addObject();
        output.put("command", "printUsers");

        ArrayNode usersArray = output.putArray("output");

        for (User user : userRegistry.getUsers()) {
            ObjectNode userNode = usersArray.addObject();
            userNode.put("firstName", user.getFirstName());
            userNode.put("lastName", user.getLastName());
            userNode.put("email", user.getEmail());

            if (user.userHasAccount() == true) {
                ArrayNode accountsArray = userNode.putArray("accounts");

                for (Account account : user.getAccounts()) {
                    ObjectNode accountNode = accountsArray.addObject();
                    accountNode.put("IBAN", account.getIBAN());
                    accountNode.put("balance", account.getBalance());
                    accountNode.put("currency", account.getCurrency());
                    accountNode.put("type", account.getType());

                    if(account.hasCards() == true) {
                        ArrayNode cardsArray = accountNode.putArray("cards");

                        for (Card card : account.getCards()) {
                            ObjectNode cardNode = cardsArray.addObject();
                            cardNode.put("cardNumber", card.getCardNumber());
                            cardNode.put("status", card.getStatus());
                        }
                    }
                    else {
                        accountNode.putArray("cards");
                    }

                }
            }
            else {
                userNode.putArray("accounts");
            }
        }
        output.put("timestamp", timestamp);
    }






}
