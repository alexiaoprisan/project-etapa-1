package org.poo.account;

public class AccountFactory {
    public enum AccountType {
        classic, savings
    }

    public static Account createAccount(AccountType accountType, String currency, String IBAN, double balance, double minBalance, String alias, double interestRate) {
        switch (accountType) {
            case classic:
                return new ClassicAccount(currency, IBAN, balance, minBalance);
            case savings:
                return new SavingsAccount(currency, IBAN, balance, minBalance, interestRate);
            default:
                throw new IllegalArgumentException("The account type " + accountType + " is not recognized.");
        }
    }
}
