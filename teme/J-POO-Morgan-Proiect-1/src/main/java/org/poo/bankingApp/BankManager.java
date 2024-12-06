package org.poo.bankingApp;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.commands.Command;
import org.poo.commands.CommandFactory;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ExchangeInput;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.UserInput;
import org.poo.user.User;
import org.poo.utils.Utils;

public class BankManager {
    UserRegistry userRegistry = new UserRegistry();
    ExchangeRates exchangeRates = new ExchangeRates();

    //Commandssss commands = new Commandssss();
    //CommandsAccount commandsAccount = new CommandsAccount();
    //CommandsCard commandsCard = new CommandsCard();

    public void processCommands(ObjectInput inputData, ArrayNode Output) {
        Utils.resetRandom();
        processUsers(inputData);
        processExchageRates(inputData);
        exchangeRates.findNewExchangeRates();

        CommandFactory commandFactory = new CommandFactory(userRegistry, Output, exchangeRates);
        for (CommandInput input : inputData.getCommands()) {
            String commandType = input.getCommand();
            Command command = commandFactory.createCommand(commandType, input);
            if (command == null) {
                continue; // Skip this iteration if the command is null
            }
            command.execute();
        }

    }

    public void processUsers(ObjectInput inputData) {
        for (UserInput user : inputData.getUsers()) {
            User newUser = new User(user.getFirstName(), user.getLastName(), user.getEmail());

            userRegistry.addUser(newUser);
        }
    }

    public void processExchageRates(ObjectInput inputData) {

        for(ExchangeInput exchange : inputData.getExchangeRates()) {
            ExchangeInputFormat exchangeInputData = new ExchangeInputFormat();
            exchangeInputData.setFrom(exchange.getFrom());
            exchangeInputData.setTo(exchange.getTo());
            exchangeInputData.setRate(exchange.getRate());
            exchangeInputData.setTimestamp(exchange.getTimestamp());

            exchangeRates.addExchangeRate(exchangeInputData);
        }
    }
}
