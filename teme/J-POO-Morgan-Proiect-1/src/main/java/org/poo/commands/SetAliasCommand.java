package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.account.Account;
import org.poo.user.UserRegistry;
import org.poo.user.User;

public class SetAliasCommand implements Command {
    private final UserRegistry userRegistry;
    private final ArrayNode output;
    private final String email;
    private final String IBAN;
    private final String alias;

    public SetAliasCommand(UserRegistry userRegistry, ArrayNode output, int timestamp, String email, String IBAN, String alias) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.email = email;
        this.IBAN = IBAN;
        this.alias = alias;
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

       account.setAlias(alias);
       // System.out.println(account.getAlias());

    }
}