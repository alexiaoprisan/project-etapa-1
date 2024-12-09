package org.poo.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Account;
import org.poo.transaction.CardPaymentTransaction;
import org.poo.transaction.Transaction;

import java.util.ArrayList;

public class ClassicReport {

    private ArrayList<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ObjectNode generateReportBetweenTimestamps(int timestampStart, int timestampEnd, int timestamp, Account account) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode output = mapper.createObjectNode();
            output.put("command", "report");

            ObjectNode accountNode = output.putObject("output");
            accountNode.put("balance", account.getBalance());
            accountNode.put("currency", account.getCurrency());
            accountNode.put("IBAN", account.getIBAN());

            ArrayNode transactionArray = accountNode.putArray("transactions");

            for (Transaction transaction : transactions) {
                if (transaction.getTimestamp() >= timestampStart && transaction.getTimestamp() <= timestampEnd) {
                    ObjectNode transactionNode = transactionArray.addObject();
                    transaction.toJson(transactionNode);
                }
            }

            output.put("timestamp", timestamp);
            return output; // Return the ObjectNode directly
        } catch (Exception e) {
            throw new RuntimeException("Error generating report", e);
        }
    }


}
