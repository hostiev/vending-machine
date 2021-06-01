package com.example.vendingmachine.tests;

import com.example.vendingmachine.goods.ItemCategory;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

class ItemCategoryTest {

    ArrayList<ItemCategory> categories = new ArrayList<>();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        categories.add(new ItemCategory("", -0.0001, -1));
        categories.add(new ItemCategory("Decent name", 15.5, 36));
        categories.add(new ItemCategory("0", 0, 0));
        categories.add(new ItemCategory("No items", 1, 0));
        categories.add(new ItemCategory("No price", 0, 1));
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        categories.clear();
    }

    @org.junit.jupiter.api.Test
    void isPurchasable() {
        Assertions.assertFalse(categories.get(0).isPurchasable());
        Assertions.assertTrue(categories.get(1).isPurchasable());
        Assertions.assertFalse(categories.get(2).isPurchasable());
        Assertions.assertFalse(categories.get(3).isPurchasable());
        Assertions.assertTrue(categories.get(4).isPurchasable());
    }

    @org.junit.jupiter.api.Test
    void getName() {
        Assertions.assertEquals("", categories.get(0).getName());
        Assertions.assertEquals("Decent name", categories.get(1).getName());
        Assertions.assertEquals("0", categories.get(2).getName());
        Assertions.assertEquals("No items", categories.get(3).getName());
        Assertions.assertEquals("No price", categories.get(4).getName());
    }

    @org.junit.jupiter.api.Test
    void getItemPrice() {
        Assertions.assertEquals(-0.0001, categories.get(0).getItemPrice());
        Assertions.assertEquals(15.5, categories.get(1).getItemPrice());
        Assertions.assertEquals(0, categories.get(2).getItemPrice());
        Assertions.assertEquals(1, categories.get(3).getItemPrice());
        Assertions.assertEquals(0, categories.get(4).getItemPrice());
    }

    @org.junit.jupiter.api.Test
    void getItemsQuantity() {
        Assertions.assertEquals(-1, categories.get(0).getItemsQuantity());
        Assertions.assertEquals(36, categories.get(1).getItemsQuantity());
        Assertions.assertEquals(0, categories.get(2).getItemsQuantity());
        Assertions.assertEquals(0, categories.get(3).getItemsQuantity());
        Assertions.assertEquals(1, categories.get(4).getItemsQuantity());
    }

    @org.junit.jupiter.api.Test
    void addItems() {
        categories.forEach(category -> {
            addItemsByCondition(category, -5);
            addItemsByCondition(category, 0);
            addItemsByCondition(category, 5);
        });
    }

    void addItemsByCondition(ItemCategory category, int newQuantity) {
        int oldQuantity = category.getItemsQuantity();
        if (newQuantity > 0) {
            Assertions.assertTrue(category.addItems(newQuantity));
            Assertions.assertEquals(oldQuantity + newQuantity, category.getItemsQuantity());
        } else {
            Assertions.assertFalse(category.addItems(newQuantity));
            Assertions.assertEquals(oldQuantity, category.getItemsQuantity());
        }
    }

    @org.junit.jupiter.api.Test
    void removeItem() {
        categories.forEach(this::removeItemByCondition);
    }

    void removeItemByCondition(ItemCategory category) {
        int oldQuantity = category.getItemsQuantity();
        if (oldQuantity > 0) {
            Assertions.assertTrue(category.removeItem());
            Assertions.assertEquals(oldQuantity - 1, category.getItemsQuantity());
        } else {
            Assertions.assertFalse(category.removeItem());
            Assertions.assertEquals(oldQuantity, category.getItemsQuantity());
        }
    }
}