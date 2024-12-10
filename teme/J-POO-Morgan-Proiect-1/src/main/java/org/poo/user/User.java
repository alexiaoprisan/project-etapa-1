package org.poo.user;

import org.poo.account.Account;
import org.poo.account.AccountFactory;
import org.poo.transaction.TransactionReport;
import org.poo.transaction.Transaction;

import java.util.ArrayList;

/**
 * Represents a user.
 * The user can have multiple accounts and each account can have multiple cards.
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;

    // the list of accounts owned by the user
    private ArrayList<Account> accounts = new ArrayList<>();
    private boolean hasAccount = false;

    // the transaction report for the user
    private TransactionReport transactionReport = new TransactionReport();

    /**
     * Constructs a User instance with the specified details.
     *
     * @param firstName the user's first name
     * @param lastName  the user's last name
     * @param email     the user's email address
     */
    public User(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Gets the user's first name.
     *
     * @return the first name of the user
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName the new first name of the user
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the user's last name.
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName the new last name of the user
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the user's email address.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the new email address of the user
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Checks if the user has an account.
     *
     * @return true if the user has an account, false otherwise
     */
    public boolean userHasAccount() {
        return hasAccount;
    }

    /**
     * Gets the list of accounts owned by the user.
     *
     * @return the list of accounts
     */
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * Adds a new account for the user.
     *
     * @param accountType the type of the account
     * @param currency    the currency of the account
     * @param iban        the IBAN of the account
     */
    public void addAccount(final String accountType, final String currency, final String iban) {
        // create a new account based on the account type, using the factory pattern
        AccountFactory.AccountType type = AccountFactory.AccountType.valueOf(accountType);
        Account newAccount = AccountFactory.createAccount(type,
                currency, iban, 0, 0, "alias", 0);

        // add the new account to the list of accounts
        accounts.add(newAccount);
        hasAccount = true;
    }

    /**
     * Gets the user instance by matching the provided email.
     *
     * @param newEmail the email to match
     * @return the user instance if the email matches, null otherwise
     */
    public User getUserByEmail(final String newEmail) {
        if (this.email.equals(newEmail)) {
            return this;
        }
        return null;
    }

    /**
     * Gets the account by its IBAN.
     *
     * @param iban the IBAN to search for
     * @return the account with the matching IBAN, or null if not found
     */
    public Account getAccountByIBAN(final String iban) {
        for (Account account : accounts) {
            if (account.getIBAN().equals(iban)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Gets the account by its alias.
     *
     * @param alias the alias to search for
     * @return the account with the matching alias, or null if not found
     */
    public Account getAccountByAlias(final String alias) {
        for (Account account : accounts) {
            if (account.getAlias() != null && account.getAlias().equals(alias)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Gets the account by the card number associated with it.
     *
     * @param cardNumber the card number to search for
     * @return the account with the matching card number, or null if not found
     */
    public Account getAccountByCardNumber(final String cardNumber) {
        for (Account account : accounts) {
            if (account.getCardByNumber(cardNumber) != null) {
                return account;
            }
        }
        return null;
    }

    /**
     * Gets the transaction report for the user.
     *
     * @return the transaction report
     */
    public TransactionReport getTransactionReport() {
        return transactionReport;
    }

    /**
     * Adds a transaction to the user's transaction report.
     *
     * @param transaction the transaction to add
     */
    public void addTransaction(final Transaction transaction) {
        transactionReport.addTransaction(transaction);
    }
}
