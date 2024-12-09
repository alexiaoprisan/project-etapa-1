package org.poo.bankingApp;

import org.poo.account.Account;
import org.poo.card.Card;
import org.poo.user.User;
import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;

public class UserRegistry {
    ArrayList<User> users = new ArrayList<User>();


    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public Account getAccountByIBAN(String IBAN) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIBAN().equals(IBAN)) {
                    return account;
                }
            }
        }
        return null;
    }

    public Account getAccountByAlias(String alias) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                //System.out.println(user.getEmail());
                if(account.getAlias() != null) {
                    if (account.getAlias().equals(alias)) {
                        System.out.println("Account found" + user.getFirstName());
                        return account;
                    }
                }
            }
        }
        return null;
    }

    public Card getCardByNumber(String cardNumber) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        return card;
                    }
                }
            }
        }
        return null;
    }

    public User getUserByCardNumber(String cardNumber) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        return user;
                    }
                }
            }
        }
        return null;
    }

    public User getUserByIBAN(String IBAN) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIBAN().equals(IBAN)) {
                    return user;
                }
            }
        }
        return null;
    }

}
