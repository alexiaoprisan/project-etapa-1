package org.poo.transaction;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SplitPaymentTransaction extends Transaction {

    private final double amount;
    private final String currency;
    private final String[] involvedAccounts;

    public SplitPaymentTransaction(int timestamp, String description, double amount, String currency, String[] involvedAccounts) {
        super(timestamp, description);
        this.amount = amount;
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String[] getInvolvedAccounts() {
        return involvedAccounts;
    }

    @Override
    public void toJson(ObjectNode node) {
        node.put("timestamp", getTimestamp());
        node.put("description", getDescription());
        node.put("currency", getCurrency());
        node.put("amount", getAmount());
        ArrayNode accountsArray = node.putArray("involvedAccounts");
        for (String account : getInvolvedAccounts()) {
            accountsArray.add(account);
        }
    }

}
