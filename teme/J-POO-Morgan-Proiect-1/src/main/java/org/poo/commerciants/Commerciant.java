package org.poo.commerciants;

import java.util.ArrayList;

public class Commerciant {
    private String name;
    private double amountSpent = 0;
    int timestamp;

    public Commerciant(String name, double amountSpent, int timestamp) {
        this.name = name;
        this.amountSpent = amountSpent;
        this.timestamp = timestamp;
    }

    public Commerciant() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmountSpent() {
        return this.amountSpent;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public void setAmountSpent(double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public void addAmountSpent(double amount) {
        this.amountSpent += amount;
    }


}
