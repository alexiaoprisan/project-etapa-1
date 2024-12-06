package org.poo.transaction;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class CardPaymentTransaction extends Transaction {
    private final double amount;
    private final String commerciant;

    public CardPaymentTransaction(int timestamp, String description, double amount, String commerciant) {
        super(timestamp, description);
        this.amount = amount;
        this.commerciant = commerciant;
    }

    public double getAmount() {
        return amount;
    }

    public String getCommerciant() {
        return commerciant;
    }

    @Override
    public void toJson(ObjectNode node) {
        node.put("amount", getAmount());
        node.put("commerciant", getCommerciant());
        node.put("description", getDescription());
        node.put("timestamp", getTimestamp());
    }


}
