package org.poo.card;

public class RegularCard implements Card {
    private String cardNumber;
    private String type = "regular";
    private String status;

    public RegularCard(String cardNumber) {
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

}
