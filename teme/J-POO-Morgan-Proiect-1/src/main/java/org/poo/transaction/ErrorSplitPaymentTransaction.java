package org.poo.transaction;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ErrorSplitPaymentTransaction extends Transaction {

    private final double amount;
    private final String currency;
    private final String[] involvedAccounts;
    private final String error;

    public ErrorSplitPaymentTransaction(int timestamp, String description, double amount, String currency, String[] involvedAccounts, String error) {
        super(timestamp, description);
        this.amount = amount;
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
        this.error = error;
    }

    public String getError() {
        return error;
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
        node.put("error", getError());
        node.put("currency", getCurrency());
        node.put("amount", getAmount());
        ArrayNode accountsArray = node.putArray("involvedAccounts");
        for (String account : getInvolvedAccounts()) {
            accountsArray.add(account);
        }
    }

}
