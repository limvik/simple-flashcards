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

    // 화면 진입 메시지 출력
    public void printFirstMessage();

    // 대기 메시지 출력
    public void printLoading();

    // 메뉴 출력
    public void printMenu();

    // 오류 메시지 출력
    public void printError();

}
