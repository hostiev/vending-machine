package com.example.vendingmachine.goods;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of a vending machine goods storage.
 * Manges items categories data.
 */
public class GoodsStorage implements Storage{

    private HashMap<String, ItemCategory> servedCategories = new HashMap<>();

    /**
     * Adds new category to the storage.
     * If there's already a category with the same name - replaces it.
     *
     * @return Returns true on success or false otherwise.
     */
    @Override
    public boolean addCategory(String name, double price, int quantity) {
        if (price <= 0 || quantity < 0) {
            return false;
        }

        servedCategories.put(name, new ItemCategory(name, price, quantity));
        return true;
    }

    /**
     * Adds specified amount of items to the specified category.
     *
     * @return Returns true on success or false otherwise.
     */
    @Override
    public boolean addItem(String categoryName, int quantity) {
        if (!categoryExists(categoryName)) {
            return false;
        }

        return servedCategories.get(categoryName).addItems(quantity);
    }

    /**
     * Gets item price of the specified category.
     *
     * @throws RuntimeException if there're no such category
     * @return Returns item price
     */
    @Override
    public double getItemPrice(String name) {
        if (!categoryExists(name)) {
            throw new RuntimeException("Category " + name + " does not exist.");
        }

        return servedCategories.get(name).getItemPrice();
    }

    /**
     * Gets item quantity of the specified category.
     *
     * @throws RuntimeException if there're no such category
     * @return Returns item quantity
     */
    @Override
    public int getItemQuantity(String name) {
        if (!categoryExists(name)) {
            throw new RuntimeException("Category " + name + " does not exist.");
        }

        return  servedCategories.get(name).getItemsQuantity();
    }

    /**
     * Gets list of served categories that are currently in the storage.
     *
     * @return Returns a list of item categories
     */
    @Override
    public List<ItemCategory> getServedCategoriesList() {
        return servedCategories.values().stream()
                .sorted(Comparator.comparing(ItemCategory::getItemsQuantity).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Clears the storage from categories with no items left.
     *
     * @return Returns arraylist with removed categories info
     */
    @Override
    public ArrayList<String> clearEmptyCategories() {
        ArrayList<String> clearedCategoriesInfo = new ArrayList<>();
        List<ItemCategory> emptyCategories = servedCategories.values().stream()
                .filter(category -> !category.isPurchasable())
                .collect(Collectors.toList());

        emptyCategories.forEach(category -> {
            clearedCategoriesInfo.add(category.getName() + " " + category.getItemPrice());
            servedCategories.remove(category.getName());
        });

        return clearedCategoriesInfo;
    }

    /**
     * Removes a single item from the specified category.
     *
     * @throws RuntimeException if there're no suh category
     * @return Returns true on success or false otherwise
     */
    @Override
    public boolean removeItem(String categoryName) {
        if (!categoryExists(categoryName)) {
            throw new RuntimeException("Category " + categoryName + " does not exist.");
        }

        ItemCategory category = servedCategories.get(categoryName);

        return category.removeItem();
    }

    /**
     * Checks if the specified category exists.
     */
    private boolean categoryExists(String name) {
        return servedCategories.get(name) != null;
    }
}