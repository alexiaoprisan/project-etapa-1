package org.poo.account;

import org.poo.card.Card;
import org.poo.card.CardFactory;
import org.poo.utils.Utils;

import java.util.ArrayList;

public class SavingsAccount implements Account {
    private String currency;
    private String accountType;
    private String IBAN;
    private double balance;
    private double minBalance;
    private String alias;
    private double interestRate; // Unique field for SavingsAccount

    private ArrayList<Card> cards = new ArrayList<>();

    // Constructor
    public SavingsAccount(String currency, String IBAN, double balance, double minBalance, double interestRate) {
        this.currency = currency;
        this.accountType = "savings";
        this.IBAN = IBAN;
        this.balance = balance;
        this.minBalance = minBalance;
        this.interestRate = interestRate;
    }

    // Getters for Account fields
    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }

    @Override
    public String getIBAN() {
        return IBAN;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public double getMinBalance() {
        return minBalance;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    // Implement setter methods
    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getType() {
        return accountType;
    }

    @Override
    public void setType(String type) {
        this.accountType = type;
    }

    // Additional method for interest rate
    public double getInterestRate() {
        return interestRate;
    }

    // Setter for balance (to allow modifying the balance)
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void addCard(Card card) {
        cards.add(card);
    }


    @Override
    public void createCard(String type, String cardNumber) {
        // Create a new card
        Card newCard = CardFactory.createCard(type, cardNumber);

        // Add the card to the list of cards
        cards.add(newCard);
    }

    @Override
    public boolean hasCards() {

        if (cards.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public Card getCardByNumber(String cardNumber) {
        for (Card card : cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }
        return null;
    }
}
