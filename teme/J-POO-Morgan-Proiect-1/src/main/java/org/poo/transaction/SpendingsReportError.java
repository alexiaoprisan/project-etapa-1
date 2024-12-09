package org.poo.transaction;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class SpendingsReportError extends Transaction {
    public SpendingsReportError(int timestamp, String description) {
        super(timestamp, description);
    }

    @Override
    public void toJson(ObjectNode node) {
        node.put("error", getDescription());
        node.put("timestamp", getTimestamp());
    }
}
