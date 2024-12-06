package org.poo.bankingApp;

public class ExchangeInputFormat {
    private String from;
    private String to;
    private double rate;
    private int timestamp;

    public ExchangeInputFormat(String from, String to, double rate, int timestamp) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.timestamp = timestamp;
    }

    public ExchangeInputFormat() {
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getRate() {
        return rate;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
