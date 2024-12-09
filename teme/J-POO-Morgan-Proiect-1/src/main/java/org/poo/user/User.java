package org.poo.user;

import org.poo.account.Account;
import org.poo.account.AccountFactory;
import org.poo.commerciants.Commerciant;
import org.poo.transaction.TransactionReport;
import org.poo.utils.Utils;
import org.poo.transaction.Transaction;

import java.util.*;


public class User {
    private String firstName;
    private String lastName;
    private String email;

    private ArrayList<Account> accounts = new ArrayList<>();
    private boolean hasAccount = false;

    private TransactionReport transactionReport = new TransactionReport();

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean userHasAccount() {
        if (hasAccount == true) {
            return true;
        }
        return false;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(String accountType, String currency, String IBAN) {
        AccountFactory.AccountType type = AccountFactory.AccountType.valueOf(accountType);

        Account newAccount = AccountFactory.createAccount(type, currency, IBAN, 0, 0, "alias", 0);
        accounts.add(newAccount);
        hasAccount = true;
    }

    public User getUserByEmail(String email) {
        if (this.email.equals(email)) {
            return this;
        }
        return null;
    }

    public Account getAccountByIBAN(String IBAN) {
        for (Account account : accounts) {
            if (account.getIBAN().equals(IBAN)) {
                return account;
            }
        }
        return null;
    }

    public Account getAccountByAlias(String alias) {
        for (Account account : accounts) {
            if(account.getAlias() != null) {
                if (account.getAlias().equals(alias)) {
                    return account;
                }

            }
        }
        return null;
    }

    public Account getAccountByCardNumber(String cardNumber) {
        for (Account account : accounts) {
            if (account.getCardByNumber(cardNumber) != null) {
                return account;
            }
        }
        return null;
    }

    public TransactionReport getTransactionReport() {
        return transactionReport;
    }

    public void addTransaction(Transaction transaction) {
        transactionReport.addTransaction(transaction);
    }





}
