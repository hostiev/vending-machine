package com.example.vendingmachine.purchases;

import java.time.LocalDate;

/**
 * Implements a single purchase.
 */
public class ItemPurchase {
    private String itemCategoryName;
    private double sellingPrice;
    private LocalDate date;

    public ItemPurchase(String itemCategoryName, double sellingPrice, LocalDate date) {
        this.itemCategoryName = itemCategoryName;
        this.sellingPrice = sellingPrice;
        this.date = date;
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public LocalDate getDate() {
        return date;
    }
}
