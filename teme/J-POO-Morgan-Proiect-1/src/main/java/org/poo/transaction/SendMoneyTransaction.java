package org.poo.transaction;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Class that represents a transaction of sending money from one account to another
 */
public class SendMoneyTransaction extends Transaction {
    private final String senderIBAN;
    private final String receiverIBAN;
    private final double amount;
    private final String transferType;
    private final String currency;

    public SendMoneyTransaction(final int timestamp,
                                final String description,
                                final String sender, final
                                String receiver,
                                final double amount,
                                final String currency,
                                final String transferType) {
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
