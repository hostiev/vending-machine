package com.example.vendingmachine.controller;

import com.example.vendingmachine.goods.GoodsStorage;
import com.example.vendingmachine.goods.ItemCategory;
import com.example.vendingmachine.goods.Storage;
import com.example.vendingmachine.output.OutputInfo;
import com.example.vendingmachine.output.VendingInfo;
import com.example.vendingmachine.purchases.ItemPurchase;
import com.example.vendingmachine.purchases.Purchase;
import com.example.vendingmachine.purchases.PurchaseHandler;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

/**
 * Controller of a vending machine.
 * Manages snack categories, prices, items amount, purchases and reports
 * via executing the following set of commands:
 * <p>addCategory "Category name" price quantity(optional)</p>
 * <p>addItem "Category name" quantity</p>
 * <p>purchase "Category name" yyyy-mm-dd</p>
 * <p>list</p>
 * <p>clear</p>
 * <p>report yyyy-mm</p>
 * <p>report yyyy-mm-dd</p>
 */
public class Controller {
    public static final String EXIT_COMMAND = "exit";
    private static final int DEFAULT_ITEMS_QUANTITY = 0;
    private Storage snackStorage = new GoodsStorage();
    private Purchase snackPurchase = new PurchaseHandler();
    private OutputInfo vendingInfo = new VendingInfo();
    private HashMap <String, Command> commands = new HashMap<>();

    public Controller() {       //todo static maybe?
        initializeCommands();
    }

    /**
     * Executes the command specified.
     *
     * @param parameters The string array with command name and parameters.
     * @return Returns true in case of success. Otherwise returns false.
     */
    public boolean executeCommand(String[] parameters){
        if (parameters.length == 0) {
            vendingInfo.showMessage("There's no command to execute.");
            return false;
        }

        String commandName = parameters[0];
        String[] commandParameters = Arrays.copyOfRange(parameters, 1, parameters.length);

        if (commands.get(commandName) == null) {
            vendingInfo.showMessage("There's no such command.");
            return false;
        }

        try {
            return commands.get(commandName).execute(commandParameters);
        } catch (Exception e) {
            vendingInfo.showMessage("Something's wrong: " + e.getMessage());
            return false;
        }
    }

    /**
     * Initializes commands via lambdas and stores them into hashmap.
     */
    private void initializeCommands() {
        /*
         * Adds new category or replaces the old one with the same name.
         * Shows info about it.
         */
        commands.put("addCategory", parameters -> {
            if (!Validator.isValidCategoryParameters(parameters)) {
                vendingInfo.showMessage("Command execution failed: invalid parameters.");
                return false;
            }

            String categoryName = parameters[0];
            double itemPrice = Double.parseDouble(parameters[1]);
            int itemsQuantity = (parameters.length > 2)
                    ? Integer.parseInt(parameters[2])
                    : DEFAULT_ITEMS_QUANTITY;

            snackStorage.addCategory(categoryName,itemPrice, itemsQuantity);

            String categoryInfo = categoryName + " " + itemPrice + " "
                    + snackStorage.getItemQuantity(categoryName);
            vendingInfo.showCategoryInfo(categoryInfo);
            return true;
        });
        /*
         * Adds items to the existing category.
         * Shows info about it.
         */
        commands.put("addItem", parameters -> {
            if (!Validator.isValidItemParameters(parameters)) {
                vendingInfo.showMessage("Command execution failed: invalid parameters.");
                return false;
            }

            String categoryName = parameters[0];
            int itemsQuantity = Integer.parseInt(parameters[1]);

            snackStorage.addItem(categoryName, itemsQuantity);

            String categoryInfo = categoryName + " " + snackStorage.getItemPrice(categoryName)
                    + " " + snackStorage.getItemQuantity(categoryName);
            vendingInfo.showCategoryInfo(categoryInfo);
            return true;
        });
        /*
         * Makes an item purchase.
         * Shows purchase info.
         */
        commands.put("purchase", parameters -> {
            if (!Validator.isValidPurchaseParameters(parameters)) {
                vendingInfo.showMessage("Command execution failed: invalid parameters.");
                return false;
            }
            String categoryName = parameters[0];
            LocalDate purchaseDate = LocalDate.parse(parameters[1], Validator.DEFAULT_DATE_FORMAT);
            double sellingPrice = snackStorage.getItemPrice(categoryName);

            if (snackStorage.removeItem(categoryName)) {
                snackPurchase.purchase(categoryName, sellingPrice, purchaseDate);
                String purchaseInfo = purchaseDate + System.lineSeparator()
                        + categoryName + " " + sellingPrice;
                vendingInfo.showPurchaseInfo(purchaseInfo);
                return true;
            } else {
                vendingInfo.showMessage(
                        "Command execution failed: there're no items left in the category.");
                return false;
            }
        });
        /*
         * Gets and shows list of served categories in the storage.
         */
        commands.put("list", parameters -> {
            List<ItemCategory> servedCategories = snackStorage.getServedCategoriesList();

            if (servedCategories == null || servedCategories.isEmpty()) {
                vendingInfo.showMessage("There're no categories.");
                return false;
            }

            vendingInfo.showServedCategoriesList(servedCategories);
            return true;
        });
        /*
         * Clears categories with no items from the storage.
         */
        commands.put("clear", parameters -> {
            ArrayList<String> clearedCategories = snackStorage.clearEmptyCategories();

            if (clearedCategories == null || clearedCategories.isEmpty()) {
                vendingInfo.showMessage("There're no categories to clear.");
                return false;
            }

            vendingInfo.showClearedCategoriesList(clearedCategories);
            return true;
        });
        /*
         * Gets purchases and forms earnings reports depending on date type.
         */
        commands.put("report", parameters -> {
            if (!Validator.isValidReportParameters(parameters)) {
                vendingInfo.showMessage("Command execution failed: invalid parameters.");
                return false;
            }

            String date = parameters[0];
            List<ItemPurchase> purchases = getPurchasesByDate(date);
            ArrayList<String> report = formEarningsReport(purchases);

            if (report == null || report.isEmpty()) {
                vendingInfo.showMessage("Nothing to report.");
                return false;
            }

            vendingInfo.showEarningsInfo(report);
            return true;
        });
    }

    /**
     * Gets purchases list depending on the date format.
     *
     * @param date The date specified
     * @return Returns a list of the purchases
     */
    private List<ItemPurchase> getPurchasesByDate(String date) {
        if (Validator.isFullDate(date)) {
            return snackPurchase.getPurchasesSinceDate(LocalDate.parse(date));
        } else if (Validator.isYearMonth(date)) {
            return snackPurchase.getPurchasesPerMonth(YearMonth.parse(date));
        }
        return null;
    }

    /**
     * Forms a report of earnings from purchases.
     *
     * @param purchases The list of purchases
     * @return Returns an arraylist with earnings info
     */
    private ArrayList<String> formEarningsReport(List<ItemPurchase> purchases) {
        if (purchases == null || purchases.isEmpty()) {
            return null;
        }

        ArrayList<String> report = new ArrayList<>();
        LinkedHashMap<String, Double> categoriesEarnings = new LinkedHashMap<>();

        // Counting earning for each category
        purchases.forEach(purchase -> {
            String categoryName = purchase.getItemCategoryName();
            if (categoriesEarnings.containsKey(categoryName)) {
                double oldSum = categoriesEarnings.get(categoryName);
                categoriesEarnings.replace(categoryName, oldSum + purchase.getSellingPrice());
            } else {
                categoriesEarnings.put(categoryName, purchase.getSellingPrice());
            }
        });

        double totalEarnings = categoriesEarnings.values().stream().reduce(0.0, Double::sum);

        //Counting items sold in each category
        categoriesEarnings.forEach((name, earnings) -> {
            long itemsSold = purchases.stream()
                    .filter(purchase -> purchase.getItemCategoryName().equals(name))
                    .count();
            report.add(name + " " + earnings + " " + itemsSold);
        });

        report.add(">Total " + totalEarnings);
        return report;
    }
}