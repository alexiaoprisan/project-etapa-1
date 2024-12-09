package org.poo.commands;

import org.poo.account.Account;
import org.poo.bankingApp.UserRegistry;
import org.poo.transaction.ErrorDeleteAccount;
import org.poo.transaction.Transaction;
import org.poo.user.User;
import org.poo.card.Card;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DeleteAccountCommand implements Command {
    private final UserRegistry userRegistry;
    private final ArrayNode Output;
    private final int timestamp;
    private final String email;
    private final String IBAN;

    public DeleteAccountCommand(UserRegistry userRegistry, ArrayNode output, int timestamp, String email, String IBAN) {
        this.userRegistry = userRegistry;
        this.Output = output;
        this.timestamp = timestamp;
        this.email = email;
        this.IBAN = IBAN;
    }

        @Override
        public void execute() {
            User user = userRegistry.getUserByEmail(email);

            if (user == null) {
                return;
            }

            Account account = user.getAccountByIBAN(IBAN);
            if (account == null) {
                return;
            }

            if (account.getBalance() > 0) {
                ObjectNode output = Output.addObject();
                output.put("command", "deleteAccount");
                ObjectNode outputNode = output.putObject("output");
                outputNode.put("error", "Account couldn't be deleted - see org.poo.transactions for details");
                outputNode.put("timestamp", timestamp);

                output.put("timestamp", timestamp);

                Transaction transaction = new ErrorDeleteAccount(timestamp, "Account couldn't be deleted - there are funds remaining");
                user.addTransaction(transaction);
                return;
            }
            else {
                user.getAccounts().remove(account);

                ObjectNode output = Output.addObject();
                output.put("command", "deleteAccount");
                ObjectNode outputNode = output.putObject("output");
                outputNode.put("success", "Account deleted");
                outputNode.put("timestamp", timestamp);

                output.put("timestamp", timestamp);
                return;
            }
        }
}
