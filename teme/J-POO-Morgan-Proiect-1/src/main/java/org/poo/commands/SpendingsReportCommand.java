package org.poo.commands;

import org.poo.account.Account;
import org.poo.account.ClassicAccount;
import org.poo.bankingApp.UserRegistry;
import org.poo.commerciants.Commerciant;
import org.poo.report.ClassicReport;
import org.poo.report.PaymentsRecord;
import org.poo.report.SpendingsReport;
import org.poo.transaction.TransactionReport;
import org.poo.user.User;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class SpendingsReportCommand implements Command {

    private final UserRegistry userRegistry;
    private final ArrayNode output;
    private final int timestamp;
    private final int startTimestamp;
    private final int endTimestamp;
    private final String IBAN;

    public SpendingsReportCommand(UserRegistry userRegistry, ArrayNode output, int startTimestamp, int endTimestamp, String account, int timestamp) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.IBAN = account;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account account = userRegistry.getAccountByIBAN(IBAN);

        if(account == null) {
            ObjectNode node = output.addObject();
            node.put("command", "spendingsReport");
            ObjectNode outputNode = node.putObject("output");
            outputNode.put("description", "Account not found");
            outputNode.put("timestamp", timestamp);
            node.put("timestamp", timestamp);
            return;
        }

        User user = userRegistry.getUserByIBAN(IBAN);

        if(account.getType().equals("classic")) {
            ClassicAccount classicAccount = (ClassicAccount) account;
            PaymentsRecord paymentsRecord = classicAccount.getPaymentsRecord();

            ArrayList<Commerciant> commerciantsList = classicAccount.getCommerciantList();

            SpendingsReport spendingsReport = new SpendingsReport(paymentsRecord, commerciantsList);

            ObjectNode node = spendingsReport.generateSpendingsReportBetweenTimestamps(startTimestamp, endTimestamp, timestamp, account, commerciantsList);
            output.add(node);
        }

    }
}
