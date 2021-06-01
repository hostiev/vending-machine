package com.example.vendingmachine.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Contains methods for parameters validation.
 */
public class Validator {
    public static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter SHORT_DATE_FORMAT
            = DateTimeFormatter.ofPattern("yyyy-MM");

    private Validator() {
    }

    /**
     * Checks if parameters for the "addCategory" command are valid.
     */
    public static boolean isValidCategoryParameters(String[] parameters) {
        if (isEmptyParameter(parameters)) {
            return false;
        }

        if (parameters.length == 3) {
            return isValidPrice(parameters[1]) && isValidQuantity(parameters[2]);
        }

        if (parameters.length == 2) {
            return isValidPrice(parameters[1]);
        }

        return false;
    }

    /**
     * Checks if parameters for the "addItem" command are valid.
     */
    public static boolean isValidItemParameters(String[] parameters) {
        if (isEmptyParameter(parameters)) {
            return false;
        }

        if (parameters.length == 2) {
            return isValidQuantity(parameters[1]);
        }

        return false;
    }

    /**
     * Checks if parameters for the "purchase" command are valid.
     */
    public static boolean isValidPurchaseParameters(String[] parameters) {
        if (isEmptyParameter(parameters)) {
            return false;
        }

        if (parameters.length == 2) {
            return isFullDate(parameters[1]);
        }

        return false;
    }

    /**
     * Checks if parameters for the "report" command are valid.
     */
    public static boolean isValidReportParameters(String[] parameters) {
        if (isEmptyParameter(parameters)) {
            return false;
        }

        return isYearMonth(parameters[0]) || isFullDate(parameters[0]);
    }

    /**
     * Checks if there are empty parameters.
     */
    public static boolean isEmptyParameter(String[] parameters) {
        for (String parameter : parameters) {
            if (parameter.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the parameter can be used as a price.
     */
    public static boolean isValidPrice(String number) {
        try {
            return Double.parseDouble(number) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the parameter can be used as a quantity.
     */
    public static boolean isValidQuantity(String number) {
        try {
            return Integer.parseInt(number) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the parameter can be parsed as YearMonth object.
     */
    public static boolean isYearMonth(String date) {
        try {
            YearMonth.parse(date, SHORT_DATE_FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks if the parameter can be parsed as LocalDate object.
     */
    public static boolean isFullDate(String date) {
        try {
            LocalDate.parse(date, DEFAULT_DATE_FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}