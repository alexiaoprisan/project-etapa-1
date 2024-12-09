package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.account.Account;
import org.poo.user.UserRegistry;

public class SetMinimumBalanceCommand implements Command {
    // "command": "setMinimumBalance",
    //            "account": "RO58POOB7344468893732422",
    //            "amount": 200,
    //            "timestamp": 5

    private final UserRegistry userRegistry;
    private final ArrayNode output;
    private final int timestamp;
    private final String IBAN;
    private final double amount;

    public SetMinimumBalanceCommand(UserRegistry userRegistry, ArrayNode output, int timestamp, String account, double amount) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.timestamp = timestamp;
        this.IBAN = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        Account account = userRegistry.getAccountByIBAN(IBAN);
        if (account == null) {
            return;
        }

        account.setMinBalance(amount);

    }

}
