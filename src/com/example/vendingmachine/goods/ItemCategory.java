package com.example.vendingmachine.goods;

/**
 * Implements an item category,
 * manages item data.
 */
public class ItemCategory {
    private String name;
    private double itemPrice;
    private int itemsQuantity;
    private boolean isPurchasable;

    /**
     * Creates new item category with the specified data.
     */
    public ItemCategory(String name, double itemPrice, int itemsQuantity) {
        this.name = name;
        this.itemPrice = itemPrice;
        this.itemsQuantity = itemsQuantity;
        isPurchasable = this.itemsQuantity > 0;
    }

    public boolean isPurchasable() {
        return isPurchasable;
    }

    public String getName() {
        return name;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemsQuantity() {
        return itemsQuantity;
    }

    public boolean addItems(int itemsQuantity) {
        if (itemsQuantity <= 0) {
            return false;
        }

        this.itemsQuantity += itemsQuantity;
        isPurchasable = this.itemsQuantity > 0;
        return true;
    }

    public boolean removeItem() {
        if (itemsQuantity <= 0) {
            return false;
        }

        itemsQuantity--;
        isPurchasable = itemsQuantity > 0;
        return true;
    }
}
