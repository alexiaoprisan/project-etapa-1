package org.poo.transaction;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class SendMoneyTransaction extends Transaction {
    private final String senderIBAN;
    private final String receiverIBAN;
    private final double amount;
    private final String transferType;
    private final String currency;

    public SendMoneyTransaction(int timestamp, String description, String sender, String receiver, double amount, String currency, String transferType) {
        super(timestamp, description);
        this.senderIBAN = sender;
        this.receiverIBAN = receiver;
        this.amount = amount;
        this.currency = currency;
        this.transferType = transferType;
    }

    public String getSenderIBAN() {
        return senderIBAN;
    }

    public String getReceiverIBAN() {
        return receiverIBAN;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTransferType() {
        return transferType;
    }



    @Override
    public void toJson(ObjectNode node) {
        node.put("amount", getAmount() + " " + getCurrency());
        node.put("description", getDescription());
        node.put("receiverIBAN", getReceiverIBAN());
        node.put("senderIBAN", getSenderIBAN());
        node.put("timestamp", getTimestamp());
        node.put("transferType", getTransferType());
    }

}
