package com.example.vendingmachine.output;

import com.example.vendingmachine.goods.ItemCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface for the information output.
 */
public interface OutputInfo {
    void showCategoryInfo(String categoryInfo);
    void showPurchaseInfo(String purchaseInfo);
    void showServedCategoriesList(List<ItemCategory> servedCategories);
    void showClearedCategoriesList(ArrayList<String> clearedCategories);
    void showEarningsInfo(ArrayList<String> report);
    void showMessage(String message);
};
