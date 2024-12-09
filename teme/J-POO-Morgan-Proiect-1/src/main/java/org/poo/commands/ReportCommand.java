package org.poo.commands;

import org.poo.account.Account;
import org.poo.account.ClassicAccount;
import org.poo.bankingApp.UserRegistry;
import org.poo.report.ClassicReport;
import org.poo.user.User;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ReportCommand implements Command {
    private final UserRegistry userRegistry;
    private final ArrayNode output;
    private final int startTimestamp;
    private final int endTimestamp;
    private final String IBAN;
    private final int timestamp;

    public ReportCommand(UserRegistry userRegistry, ArrayNode output, int startTimestamp, int endTimestamp, String account, int timestamp) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.IBAN = account;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        Account account = userRegistry.getAccountByIBAN(IBAN);
        if(account == null) {
            ObjectNode node = output.addObject();
            node.put("command", "report");
            ObjectNode outputNode = node.putObject("output");
            outputNode.put("description", "Account not found");
            outputNode.put("timestamp", timestamp);
            node.put("timestamp", timestamp);
            return;
        }

        User user = userRegistry.getUserByIBAN(IBAN);

        if(account.getType().equals("classic")) {
            ClassicAccount classicAccount = (ClassicAccount) account;
            ClassicReport classicReport = classicAccount.getReport();

            ObjectNode node = classicReport.generateReportBetweenTimestamps(startTimestamp, endTimestamp, timestamp, account);
            output.add(node);

        }
    }

}
