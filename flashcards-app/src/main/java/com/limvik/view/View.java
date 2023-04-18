package com.limvik.view;

public interface View {
    
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    public static void pause(int seconds) {

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }

    } 

}
