package org.poo.account;

import org.poo.card.Card;
import java.util.ArrayList;

public interface Account {
    // Methods to get account details
    String getCurrency();
    String getAccountType();
    String getIBAN();
    double getBalance();
    double getMinBalance();
    String getAlias();
    String getType();

    // Methods to set account details
    void setCurrency(String currency);
    void setAccountType(String accountType);
    void setIBAN(String IBAN);
    void setBalance(double balance);
    void setMinBalance(double minBalance);
    void setAlias(String alias);
    void setType(String type);

    void createCard(String type, String cardNumber);
    boolean hasCards();
    Card getCardByNumber(String cardNumber);

    ArrayList<Card> getCards();
}
