package org.poo.transaction;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class NewCardCreatedTransaction extends Transaction {
    private final String IBAN;
    private final String cardNumber;
    private final String email;

    public NewCardCreatedTransaction(int timestamp, String description, String IBAN, String cardNumber, String email) {
        super(timestamp, description);
        this.IBAN = IBAN;
        this.cardNumber = cardNumber;
        this.email = email;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void toJson(ObjectNode node) {
        node.put("account", getIBAN());
        node.put("card", getCardNumber());
        node.put("cardHolder", getEmail());
        node.put("description", getDescription());
        node.put("timestamp", getTimestamp());
    }
}
