package org.poo.transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Account;
import org.poo.commerciants.Commerciant;

import java.util.ArrayList;
import java.util.List;

public class TransactionReport {
    
    private final List<Transaction> transactions;

    public TransactionReport() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public ObjectNode generateReport(int timestamp) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode output = mapper.createObjectNode();
            output.put("command", "printTransactions");

            ArrayNode transactionArray = output.putArray("output");
            for (Transaction transaction : transactions) {
                ObjectNode transactionNode = transactionArray.addObject();
                transaction.toJson(transactionNode);
            }

            output.put("timestamp", timestamp);
            return output; // Return the ObjectNode directly
        } catch (Exception e) {
            throw new RuntimeException("Error generating report", e);
        }
    }

}
