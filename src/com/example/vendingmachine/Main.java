package com.example.vendingmachine;

import com.example.vendingmachine.controller.Controller;

import java.util.Scanner;

/**
 * Vending machine main class.
 *
 * This program is an implementation of a very basic snack vending machine.
 * It manages different snack categories, controls the price and amount of
 * available items in each category, handles purchases, and provides related
 * statistics.
 */
public class Main {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Controller vendingMachineController = new Controller();

        showInfo();

        String command = in.nextLine();

        while (!command.equalsIgnoreCase(Controller.EXIT_COMMAND)) {
            String[] parsedCommand = parseCommand(command);

            vendingMachineController.executeCommand(parsedCommand);

            command = in.nextLine();
        }
    }

    /**
     * Shows available commands info.
     */
    private static void showInfo() {
        System.out.println("Available commands and their parameters:" + System.lineSeparator()
                + "addCategory \"Category name\" price quantity(optional)"
                + System.lineSeparator()
                + "addItem \"Category name\" quantity" + System.lineSeparator()
                + "purchase \"Category name\" yyyy-mm-dd" + System.lineSeparator()
                + "List" + System.lineSeparator()
                + "clear" + System.lineSeparator()
                + "report yyyy-mm" + System.lineSeparator()
                + "report yyyy-mm-dd" + System.lineSeparator()
                + Controller.EXIT_COMMAND + System.lineSeparator() + System.lineSeparator()
                + "Please enter command:");
    }

    /**
     * Parses command name and its parameters from string.
     *
     * RegEx adapted from source:
     * https://stackabuse.com/regex-splitting-by-character-unless-in-quotes/
     *
     * @param command The string to be parsed
     * @return Returns string array with parsed command, where [0] - command name.
     */
    private static String[] parseCommand(String command) {
        String[] parsedCommand = command
                .split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        for (int i = 0; i < parsedCommand.length; i++) {
            parsedCommand[i] = parsedCommand[i].replace("\"", "").trim();
        }

        return parsedCommand;
    }
}
