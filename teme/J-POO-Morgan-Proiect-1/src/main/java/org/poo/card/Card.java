package org.poo.card;

public interface Card {
    String getCardNumber();
    String getType(); // "regular" or "oneTimePay"
    String getStatus();

    void setStatus(String status);
    void setCardNumber(String cardNumber);
    void setType(String type);
}
