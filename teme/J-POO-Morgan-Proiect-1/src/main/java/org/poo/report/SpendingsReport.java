package org.poo.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Account;
import org.poo.commerciants.Commerciant;
import org.poo.transaction.Transaction;
import java.util.ArrayList;


public class SpendingsReport {
    PaymentsRecord paymentsRecord;
    ArrayList<Commerciant> commerciantsList;

    public SpendingsReport(PaymentsRecord paymentsRecord, ArrayList<Commerciant> commerciantsList) {
        this.paymentsRecord = paymentsRecord;
        this.commerciantsList = commerciantsList;
    }

    public ObjectNode generateSpendingsReportBetweenTimestamps(int timestampStart, int timestampEnd, int timestamp, Account account, ArrayList<Commerciant> commerciantsList) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode output = mapper.createObjectNode();
            output.put("command", "spendingsReport");

            ObjectNode accountNode = output.putObject("output");
            accountNode.put("balance", account.getBalance());

            ArrayNode commerciantsArray = accountNode.putArray("commerciants");
            for (Commerciant commerciant : commerciantsList) {
                if (commerciant.getTimestamp() >= timestampStart && commerciant.getTimestamp() <= timestampEnd) {
                    ObjectNode commerciantNode = commerciantsArray.addObject();
                    commerciantNode.put("commerciant", commerciant.getName());
                    commerciantNode.put("total", commerciant.getAmountSpent());
                }
            }

            accountNode.put("currency", account.getCurrency());
            accountNode.put("IBAN", account.getIBAN());

            ArrayNode transactionArray = accountNode.putArray("transactions");

            ArrayList<Transaction> record = paymentsRecord.getTransactions();

            for (Transaction transaction : record) {
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
