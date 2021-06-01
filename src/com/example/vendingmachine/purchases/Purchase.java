package com.example.vendingmachine.purchases;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * The interface for purchase handling and reports.
 */
public interface Purchase {
    boolean purchase(String itemCategoryName, double sellingPrice, LocalDate date);
    List<ItemPurchase> getPurchasesPerMonth(YearMonth date);
    List<ItemPurchase> getPurchasesSinceDate(LocalDate date);
}
