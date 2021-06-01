package com.example.vendingmachine.purchases;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles purchases and purchases reports.
 */
public class PurchaseHandler implements Purchase{

    ArrayList<ItemPurchase> purchases = new ArrayList<>();

    /**
     * Stores purchase data.
     *
     * @return Returns true on success or false otherwise
     */
    @Override
    public boolean purchase(String itemCategoryName, double sellingPrice, LocalDate date) {
        return purchases.add(new ItemPurchase(itemCategoryName, sellingPrice, date));
    }

    /**
     * Gets purchases made in specified month.
     *
     * @return Returns a list of purchases
     */
    @Override
    public List<ItemPurchase> getPurchasesPerMonth(YearMonth date) {
        return purchases.stream()
                .filter(purchase -> {
                    int purchaseYear = purchase.getDate().getYear();
                    Month purchaseMonth = purchase.getDate().getMonth();
                    return date.getYear() == purchaseYear && date.getMonth().equals(purchaseMonth);
                })
                .sorted(Comparator.comparing(ItemPurchase::getItemCategoryName))
                .collect(Collectors.toList());
    }

    /**
     * Gets purchases made since specified data.
     *
     * @return Returns a list of purchases
     */
    @Override
    public List<ItemPurchase> getPurchasesSinceDate(LocalDate date) {
        return purchases.stream()
                .filter(purchase -> {
                    LocalDate purchaseDate = purchase.getDate();
                    return purchaseDate.isAfter(date) || purchaseDate.isEqual(date);
                })
                .sorted(Comparator.comparing(ItemPurchase::getItemCategoryName))
                .collect(Collectors.toList());
    }
}
