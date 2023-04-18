package com.limvik.view;

public class MainView {

    private static final String WELCOME = "Welcome to Limvik's Flashcards";

    private MainView() { }

    // 프로그램 시작 인사
    public static void printGreeting() {
        System.out.println(WELCOME);
    }

}
