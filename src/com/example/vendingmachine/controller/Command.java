package com.example.vendingmachine.controller;

/**
 * Interface of a command function.
 */
public interface Command {
    boolean execute(String[] parameters);
}
