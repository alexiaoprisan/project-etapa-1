package org.poo.card;

public class OneTimePayCard implements Card {
    private String cardNumber;
    private String type = "oneTimePay";
    private String status;

    private boolean used = false;


    public OneTimePayCard(String cardNumber) {
        this.cardNumber = cardNumber;
        this.status = "active"; // Status initializat la "active"
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }


    public boolean isUsed() {
        return used;
    }

    public void useCard() throws IllegalStateException {
        if (used) {
            throw new IllegalStateException("The one-time pay card has already been used.");
        }
        used = true; // Marcare ca folosit
    }

}
