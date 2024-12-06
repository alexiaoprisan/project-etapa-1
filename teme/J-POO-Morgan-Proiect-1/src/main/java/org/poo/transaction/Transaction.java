package org.poo.transaction;

import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class Transaction {

    private final int timestamp;
    private final String description;

    public Transaction(int timestamp, String description) {
        this.timestamp = timestamp;
        this.description = description;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    // Abstract method to convert the transaction to JSON
    public abstract void toJson(ObjectNode node);
}
