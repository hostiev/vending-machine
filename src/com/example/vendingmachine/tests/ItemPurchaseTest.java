package com.example.vendingmachine.tests;

import com.example.vendingmachine.purchases.ItemPurchase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ItemPurchaseTest {

    ArrayList<ItemPurchase> purchases = new ArrayList<>();

    @BeforeEach
    void setUp() {
        purchases.add(new ItemPurchase("", -1.005, LocalDate.parse("0001-01-01")));
        purchases.add(new ItemPurchase("Some category", 35.1, LocalDate.parse("1879-01-02")));
        purchases.add(new ItemPurchase("Another category", 0, LocalDate.parse("2001-12-31")));
    }

    @AfterEach
    void tearDown() {
        purchases.clear();
    }

    @Test
    void getItemCategoryName() {
        Assertions.assertEquals("", purchases.get(0).getItemCategoryName());
        Assertions.assertEquals("Some category", purchases.get(1).getItemCategoryName());
        Assertions.assertEquals("Another category", purchases.get(2).getItemCategoryName());
    }

    @Test
    void getSellingPrice() {
        Assertions.assertEquals(-1.005, purchases.get(0).getSellingPrice());
        Assertions.assertEquals(35.1, purchases.get(1).getSellingPrice());
        Assertions.assertEquals(0, purchases.get(2).getSellingPrice());
    }

    @Test
    void getDate() {
        Assertions.assertEquals(LocalDate.parse("0001-01-01"), purchases.get(0).getDate());
        Assertions.assertEquals(LocalDate.parse("1879-01-02"), purchases.get(1).getDate());
        Assertions.assertEquals(LocalDate.parse("2001-12-31"), purchases.get(2).getDate());
    }
}