package org.poo.account;

import org.poo.card.Card;
import org.poo.card.CardFactory;
import org.poo.commerciants.Commerciant;
import org.poo.report.ClassicReport;
import org.poo.report.PaymentsRecord;
import org.poo.transaction.Transaction;

import java.util.ArrayList;

public class ClassicAccount implements Account {
    private String currency;
    private String accountType;
    private String IBAN;
    private double balance;
    private double minBalance;
    private String alias;

    private ArrayList<Commerciant> commerciantsList = new ArrayList<>();
    private ClassicReport report = new ClassicReport();
    private PaymentsRecord paymentsRecord = new PaymentsRecord();

    private ArrayList<Card> cards = new ArrayList<>();


    // Constructor
    public ClassicAccount(String currency, String IBAN, double balance, double minBalance) {
        this.currency = currency;
        this.IBAN = IBAN;
        this.balance = balance;
        this.minBalance = minBalance;
        this.accountType = "classic";
    }

    // Implement getter methods
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

    public ArrayList<Commerciant> getCommerciantList() {
        return commerciantsList;
    }

    public void addCommerciant(Commerciant commerciant) {
        // Check for duplicate
        for (Commerciant c : commerciantsList) {
            if (c.getName().equals(commerciant.getName())) {
                return; // Do not add duplicate
            }
        }

        // Find the correct position to insert
        int index = 0;
        for (Commerciant c : commerciantsList) {
            if (c.getName().compareTo(commerciant.getName()) > 0) {
                break; // Found the position where commerciant should be inserted
            }
            index++;
        }

        // Add to the list at the calculated position
        commerciantsList.add(index, commerciant);
    }


    public ClassicReport getReport() {
        return report;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        // Update the balance
        report.addTransaction(transaction);
    }

    public void addPayment(Transaction transaction) {
        paymentsRecord.addTransaction(transaction);
    }

    public PaymentsRecord getPaymentsRecord() {
        return paymentsRecord;
    }

}
