package org.poo.commands;

import org.poo.account.Account;
import org.poo.bankingApp.UserRegistry;
import org.poo.fileio.CommandInput;
import org.poo.user.User;
import org.poo.card.Card;
import org.poo.bankingApp.ExchangeRates;
import org.poo.fileio.CommerciantInput;
import org.poo.transaction.Transaction;
import org.poo.transaction.TransactionReport;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CommandFactory {
    private final UserRegistry userRegistry;
    public final ExchangeRates exchangeRates;
    private final ArrayNode output;

    public CommandFactory(UserRegistry userRegistry, ArrayNode output, ExchangeRates exchangeRates) {
        this.userRegistry = userRegistry;
        this.output = output;
        this.exchangeRates = exchangeRates;
    }

    public Command createCommand(String commandType, CommandInput input) {
        int timestamp = input.getTimestamp();
        switch (commandType) {
            case "printUsers":
                return new PrintUsersCommand(userRegistry, output, timestamp);

            case "addAccount":
                return new AddAccountCommand(userRegistry, output, timestamp,
                        input.getEmail(), input.getAccountType(), input.getCurrency());

            case "createCard":
                return new CreateCardCommand(userRegistry, output, timestamp,
                        input.getEmail(), input.getAccount(), "regular");

            case "createOneTimeCard":
                return new CreateCardCommand(userRegistry, output, timestamp,
                        input.getEmail(), input.getAccount(), "oneTimePay");

            case "addFunds":
                return new AddFundsCommand(userRegistry, output, timestamp,
                        input.getAccount(), input.getAmount());

            case "deleteAccount":
                return new DeleteAccountCommand(userRegistry, output, timestamp,
                        input.getEmail(), input.getAccount());

            case "deleteCard":
                return new DeleteCardCommand(userRegistry, output, timestamp,
                        input.getEmail(), input.getCardNumber());

            case "payOnline":
                return new PayOnlineCommand(userRegistry, exchangeRates, output, timestamp,
                        input.getCardNumber(), input.getAmount(), input.getCurrency(),
                        input.getDescription(), input.getCommerciant(), input.getEmail());

            case "sendMoney":
                return new SendMoneyCommand(userRegistry, output, timestamp,
                        input.getAccount(), input.getReceiver(), input.getAmount(),
                        input.getEmail(), input.getDescription(), exchangeRates);

            case "printTransactions":
                return new PrintTransactionsCommand(userRegistry, output, timestamp,
                        input.getEmail());

            case "setAlias":
                return new SetAliasCommand(userRegistry, output, timestamp,
                        input.getEmail(), input.getAccount(), input.getAlias());

            case "checkCardStatus":
                return new CheckCardStatusCommand(userRegistry, output, input.getCardNumber(), timestamp);

            case "setMinimumBalance":
                return new SetMinimumBalanceCommand(userRegistry, output, timestamp,
                        input.getAccount(), input.getAmount());

            default:
                // Log unknown command type and skip
                return null;
        }

    }
}
