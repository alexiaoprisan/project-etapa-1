package org.poo.account;

/**
 * Factory class to create instances of different account types.
 * This class cannot be instantiated.
 * The accounts created by this factory are of type classic or savings.
 */
public final class AccountFactory {

    // Prevent instantiation
    private AccountFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Enum representing the existing account types.
     */
    public enum AccountType {
        classic, savings
    }

    /**
     * Creates an account of the specified type (classic or savings).
     *
     * @param accountType  the type of account to create (classic or savings)
     * @param currency     the currency of the account
     * @param iban         the IBAN of the account
     * @param balance      the initial balance of the account
     * @param alias        the alias of the account
     * @param minBalance   the minimum balance of the account
     *                     (the balance cannot go below this value)
     * @param interestRate the interest rate (only applicable for savings accounts)
     * @return the created account
     * @throws IllegalArgumentException if the account type is not of type classic or savings
     */
    public static Account createAccount(final AccountType accountType,
                                        final String currency,
                                        final String iban,
                                        final double balance,
                                        final double minBalance,
                                        final String alias,
                                        final double interestRate) {
        switch (accountType) {
            case classic:
                return new ClassicAccount(currency, iban, balance, minBalance);
            case savings:
                return new SavingsAccount(currency, iban, balance, minBalance, interestRate);
            default:
                throw new IllegalArgumentException("The account type "
                        + accountType + " is not recognized.");
        }
    }
}
