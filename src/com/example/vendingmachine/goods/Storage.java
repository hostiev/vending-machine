package com.example.vendingmachine.goods;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface for a storage.
 * The storage manages categories, items amount, prices and statistics reports.
 */
public interface Storage {
    boolean addCategory(String name, double price, int quantity);
    boolean addItem(String name, int quantity);
    double getItemPrice(String name);
    int getItemQuantity(String name);
    boolean removeItem(String name);
    List<ItemCategory> getServedCategoriesList();
    ArrayList<String> clearEmptyCategories();
}
