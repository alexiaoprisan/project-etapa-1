package org.poo.transaction;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class WarningForPay extends Transaction {

    public WarningForPay(int timestamp, String description) {
        super(timestamp, description);
    }

    @Override
    public void toJson(ObjectNode node) {
        node.put("description", getDescription());
        node.put("timestamp", getTimestamp());
    }
}
