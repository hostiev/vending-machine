package com.example.vendingmachine.output;

import com.example.vendingmachine.goods.ItemCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages vending machine output info.
 */
public class VendingInfo implements OutputInfo{
    /**
     * Shows info about category.
     */
    @Override
    public void showCategoryInfo(String categoryInfo) {
        System.out.println(categoryInfo + System.lineSeparator());
    }

    /**
     * Shows info about purchase.
     */
    @Override
    public void showPurchaseInfo(String purchaseInfo) {
        System.out.println(purchaseInfo + System.lineSeparator());
    }

    /**
     * Shows list of served categories.
     */
    @Override
    public void showServedCategoriesList(List<ItemCategory> servedCategories) {
        servedCategories.forEach(category ->
                System.out.println(category.getName() + " " + category.getItemPrice() + " "
                + category.getItemsQuantity()));
        System.out.println();
    }

    /**
     * Shows info about cleared categories.
     */
    @Override
    public void showClearedCategoriesList(ArrayList<String> clearedCategories) {
        clearedCategories.forEach(System.out::println);
        System.out.println();
    }

    /**
     * Shows info about categories earnings.
     */
    @Override
    public void showEarningsInfo(ArrayList<String> report) {
        report.forEach(System.out::println);
        System.out.println();
    }

    /**
     * Shows specified text message.
     */
    @Override
    public void showMessage(String message) {
        System.out.println(message + System.lineSeparator());
    }
}
