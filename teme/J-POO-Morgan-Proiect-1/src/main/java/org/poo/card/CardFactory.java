package org.poo.card;

import org.poo.utils.Utils;

public class CardFactory {

    public static Card createCard(String type, String cardNumber) {
        switch (type) {
            case "oneTimePay":
                return new OneTimePayCard(cardNumber);
            case "regular":
                return new RegularCard(cardNumber);
            default:
                throw new IllegalArgumentException("The card type " + type + " is not recognized.");
        }
    }
}