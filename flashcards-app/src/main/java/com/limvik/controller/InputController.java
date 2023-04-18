package com.limvik.controller;

import java.util.Scanner;

public class InputController {
    private static InputController instance;
    private Scanner scanner;

    private InputController() {
        scanner = new Scanner(System.in);
    }

    public static InputController getInstance() {
        if (instance == null) {
            instance = new InputController();
        }
        return instance;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void closeScanner() {
        scanner.close();
    }
}
