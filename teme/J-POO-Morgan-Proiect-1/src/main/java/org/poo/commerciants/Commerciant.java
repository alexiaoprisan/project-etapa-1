package org.poo.commerciants;

/**
 * The Commerciant class represents a commerciant, which
 * appears in a payOnline transaction.
 * It will be used for the spendings report.
 */
public final class Commerciant {
    private String name;
    private double amountSpent = 0;
    private int timestamp;

    /**
     * Constructor for the Commerciant class.
     *
     * @param name        The name of the commerciant
     * @param amountSpent The amount spent by the account
     * @param timestamp   The timestamp of the transaction
     */
    public Commerciant(final String name, final double amountSpent, final int timestamp) {
        this.name = name;
        this.amountSpent = amountSpent;
        this.timestamp = timestamp;
    }

    /**
     * Constructor for the Commerciant class.
     */
    public Commerciant() {
    }

    /**
     * Getter for the name of the commerciant.
     *
     * @return The name of the commerciant.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the timestamp of the commerciant.
     *
     * @return The timestamp of the commerciant.
     */
    public int getTimestamp() {
        return this.timestamp;
    }

    /**
     * Getter for the amount spent by the user.
     *
     * @return The amount spent by the user.
     */
    public double getAmountSpent() {
        return this.amountSpent;
    }

    /**
     * Setter for the name of the commerciant.
     *
     * @param name The name of the commerciant.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Setter for the amount spent by the user.
     *
     * @param amountSpent The amount spent by the user.
     */
    public void setAmountSpent(final double amountSpent) {
        this.amountSpent = amountSpent;
    }

    /**
     * Setter for the timestamp of the commerciant.
     *
     * @param timestamp The timestamp of the commerciant.
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Method to add an amount spent by the user.
     *
     * @param amount The amount to be added to the total amount spent
     *               when buying from the commerciant.
     */
    public void addAmountSpent(final double amount) {
        this.amountSpent += amount;
    }


}
