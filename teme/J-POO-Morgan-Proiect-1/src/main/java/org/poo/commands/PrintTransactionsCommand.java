package org.poo.commands;

import org.poo.user.UserRegistry;
import org.poo.transaction.TransactionReport;
import org.poo.user.User;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PrintTransactionsCommand implements Command {
    private final UserRegistry userRegistry;
    private final String email;
    private final int timestamp;
    private final ArrayNode output;

    public PrintTransactionsCommand(UserRegistry userRegistry, ArrayNode output, int timestamp, String email) {
        this.userRegistry = userRegistry;
        this.email = email;
        this.timestamp = timestamp;
        this.output = output;
    }

    @Override
    public void execute() {
        User user = userRegistry.getUserByEmail(email);
        if (user == null) {
            return;
        }

        TransactionReport transactionReport = user.getTransactionReport();
        ObjectNode node = transactionReport.generateReport(timestamp);
        output.add(node);


    }

}
