package com.example.vendingmachine.tests;

import com.example.vendingmachine.purchases.ItemPurchase;
import com.example.vendingmachine.purchases.PurchaseHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

class PurchaseHandlerTest {

    PurchaseHandler purchases;

    @BeforeEach
    void setUp() {
        purchases = new PurchaseHandler();
        purchases.purchase("", -1.005, LocalDate.parse("0001-01-01"));
        purchases.purchase("Some category", 35.1, LocalDate.parse("1879-01-02"));
        purchases.purchase("Another category", 0, LocalDate.parse("2001-12-31"));
        purchases.purchase("One more category", -6, LocalDate.parse("2001-12-01"));
        purchases.purchase("One more category", 12, LocalDate.parse("2001-12-17"));
    }

    @AfterEach
    void tearDown() {
        purchases = new PurchaseHandler();
    }

    @Test
    void purchase() {
        Assertions.assertNotNull(purchases);
    }

    @Test
    void getPurchasesPerMonth() {
        List<ItemPurchase> purchasesList = purchases.getPurchasesPerMonth(
                YearMonth.parse("2021-04"));
        Assertions.assertEquals(0, purchasesList.size());

        purchasesList = purchases.getPurchasesPerMonth(YearMonth.parse("2001-12"));
        Assertions.assertEquals(3, purchasesList.size());
    }

    @Test
    void getPurchasesSinceDate() {
        List<ItemPurchase> purchasesList = purchases.getPurchasesSinceDate(
                LocalDate.parse("2021-04-02"));
        Assertions.assertEquals(0, purchasesList.size());

        purchasesList = purchases.getPurchasesSinceDate(LocalDate.parse("0001-01-01"));
        Assertions.assertEquals(5, purchasesList.size());
    }
}