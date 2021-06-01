package com.example.vendingmachine.tests;

import com.example.vendingmachine.goods.GoodsStorage;
import com.example.vendingmachine.goods.ItemCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GoodsStorageTest {

    GoodsStorage storage = new GoodsStorage();

    @BeforeEach
    void setUp() {
        storage.addCategory("Decent name", 15.5, 36);
        storage.addCategory("No items", 1, 0);
    }

    @AfterEach
    void tearDown() {
        storage = new GoodsStorage();
    }

    @Test
    void addCategory() {
        Assertions.assertFalse(storage.addCategory("", -0.0001, -1));
        Assertions.assertTrue(storage.addCategory("Decent name", 15.5, 36));
        Assertions.assertFalse(storage.addCategory("0", 0, 0));
        Assertions.assertTrue(storage.addCategory("No items", 1, 0));
        Assertions.assertFalse(storage.addCategory("No price", 0, 1));
        Assertions.assertEquals(2, storage.getServedCategoriesList().size());
    }

    @Test
    void addItem() {
        Assertions.assertFalse(storage.addItem("", 1));

        int oldQuantity = storage.getItemQuantity("Decent name");
        Assertions.assertTrue(storage.addItem("Decent name", 2));
        Assertions.assertEquals(oldQuantity + 2,
                storage.getItemQuantity("Decent name"));

        Assertions.assertFalse(storage.addItem("Decent name", 0));
        Assertions.assertEquals(oldQuantity + 2,
                storage.getItemQuantity("Decent name"));

        Assertions.assertFalse(storage.addItem("Decent name", -2));
        Assertions.assertEquals(oldQuantity + 2,
                storage.getItemQuantity("Decent name"));
    }

    @Test
    void getItemPrice() {
        Assertions.assertThrows(RuntimeException.class, () -> storage.getItemPrice(""));

        Assertions.assertEquals(15.5, storage.getItemPrice("Decent name"));
        Assertions.assertEquals(1, storage.getItemPrice("No items"));
    }

    @Test
    void getItemQuantity() {
        Assertions.assertThrows(RuntimeException.class, () -> storage.getItemQuantity(""));

        Assertions.assertEquals(36, storage.getItemQuantity("Decent name"));
        Assertions.assertEquals(0, storage.getItemQuantity("No items"));
    }

    @Test
    void getServedCategoriesList() {
        ArrayList<ItemCategory> expectedList = new ArrayList<>();
        expectedList.add(new ItemCategory("Decent name", 15.5, 36));
        expectedList.add(new ItemCategory("No items", 1, 0));
        List<ItemCategory> actualList = storage.getServedCategoriesList();
        Iterator<ItemCategory> expectedIterator = expectedList.iterator();
        Iterator<ItemCategory> actualIterator = actualList.iterator();

        while (expectedIterator.hasNext() && actualIterator.hasNext()) {
            Assertions.assertEquals(expectedIterator.next().getName(),
                    actualIterator.next().getName());
        }
    }

    @Test
    void clearEmptyCategories() {
        Assertions.assertEquals(0, storage.getItemQuantity("No items"));
        ArrayList<String> clearedCategories =  storage.clearEmptyCategories();
        Assertions.assertThrows(RuntimeException.class, () -> storage.getItemQuantity("No items"));
        Assertions.assertEquals(1, storage.getServedCategoriesList().size());
        Assertions.assertEquals("No items 1.0", clearedCategories.get(0));
    }

    @Test
    void removeItem() {
        Assertions.assertThrows(RuntimeException.class, () -> storage.removeItem(""));
        Assertions.assertTrue(storage.removeItem("Decent name"));
        Assertions.assertEquals(35, storage.getItemQuantity("Decent name"));
    }
}