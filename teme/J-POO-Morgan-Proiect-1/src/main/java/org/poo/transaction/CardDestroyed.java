package org.poo.transaction;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class CardDestroyed extends Transaction {

    private String account;
    private String card;
    private String cardHolder;

    public CardDestroyed(int timestamp, String description, String account, String card, String cardHolder) {
        super(timestamp, description);
        this.account = account;
        this.card = card;
        this.cardHolder = cardHolder;
    }

    @Override
    public void toJson(ObjectNode node) {
        node.put("account", account);
        node.put("card", card);
        node.put("cardHolder", cardHolder);
        node.put("description", getDescription());
        node.put("timestamp", getTimestamp());
    }
}
