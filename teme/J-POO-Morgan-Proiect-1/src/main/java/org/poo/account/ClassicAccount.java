package org.poo.account;

import org.poo.card.Card;
import org.poo.card.CardFactory;
import org.poo.commerciants.Commerciant;
import org.poo.report.ClassicReport;
import org.poo.report.PaymentsRecord;
import org.poo.transaction.Transaction;

import java.util.ArrayList;

/**
 * Class which represents a classic account with specific fields and methods
 * for account operations.
 * Implements the Account interface.
 * <p>
 * This class is not designed for extension.
 */
public final class ClassicAccount implements Account {
    private String currency;
    private String accountType;
    private String iban;
    private double balance;
    private double minBalance;
    private String alias;

    // cards is a list of all the cards that the user has in a specific account
    private final ArrayList<Card> cards = new ArrayList<>();

    // commerciantsList is a list of all the commerciants that the user has sent money to
    private final ArrayList<Commerciant> commerciantsList = new ArrayList<>();

    // report is a list of all the transactions made by the user
    private final ClassicReport report = new ClassicReport();

    // paymentsRecord is a list of all the payments made by the user (payOnline type)
    private final PaymentsRecord paymentsRecord = new PaymentsRecord();

    /**
     * Constructor for the ClassicAccount class.
     *
     * @param currency   the currency of the account
     * @param iban       the IBAN of the account
     * @param balance    the balance of the account (the amount of money in the account)
     * @param minBalance the minimum balance of the account
     */
    public ClassicAccount(final String currency, final String iban,
                          final double balance, final double minBalance) {
        this.currency = currency;
        this.iban = iban;
        this.balance = balance;
        this.minBalance = minBalance;
        this.accountType = "classic";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrency() {
        return currency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAccountType() {
        return accountType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIBAN() {
        return iban;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBalance() {
        return balance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMinBalance() {
        return minBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlias() {
        return alias;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIBAN(final String newIban) {
        this.iban = newIban;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setBalance(final double balance) {
        this.balance = balance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinBalance(final double minBalance) {
        this.minBalance = minBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAlias(final String alias) {
        this.alias = alias;
    }

    @Override
    public String getType() {
        return accountType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCard(final Card card) {
        cards.add(card);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createCard(final String type, final String cardNumber) {
        // Create a new card
        Card newCard = CardFactory.createCard(type, cardNumber);

        // Add the card to the list of cards
        cards.add(newCard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasCards() {
        return !cards.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Card getCardByNumber(final String cardNumber) {
        for (Card card : cards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTransaction(final Transaction transaction) {
        report.addTransaction(transaction);
    }

    /**
     * Getter for the list of commerciants, which the user has sent money to
     * in online transactions.
     *
     * @return the list of commerciants
     */
    public ArrayList<Commerciant> getCommerciantList() {
        return commerciantsList;
    }

    /**
     * Method to add a commerciant to the list of commerciants.
     *
     * @param commerciant
     */
    public void addCommerciant(final Commerciant commerciant) {
        for (Commerciant c : commerciantsList) {
            if (c.getName().equals(commerciant.getName())) {
                return;
            }
        }

        int index = 0;
        for (Commerciant c : commerciantsList) {
            if (c.getName().compareTo(commerciant.getName()) > 0) {
                break;
            }
            index++;
        }
        commerciantsList.add(index, commerciant);
    }

    /**
     * Getter for the report of the account.
     * The report contains all the transactions made by the user with
     * a specific account.
     *
     * @return the report of the account
     */
    public ClassicReport getReport() {
        return report;
    }

    /**
     * Method to add a transaction to the payments record of the account.
     * It will be useful for the spending report, which contains only
     * the transactions made in online payments.
     *
     * @param transaction
     */
    public void addPayment(final Transaction transaction) {
        paymentsRecord.addTransaction(transaction);
    }

    /**
     * Getter for the payments record of the account.
     * The payments record contains all the transactions made by the user
     * in online payments.
     *
     * @return the payments record of the account
     */
    public PaymentsRecord getPaymentsRecord() {
        return paymentsRecord;
    }
}
