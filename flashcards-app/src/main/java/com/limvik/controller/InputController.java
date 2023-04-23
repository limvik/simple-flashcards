package com.limvik.controller;

import java.util.Scanner;
import java.util.regex.Pattern;

import com.limvik.enums.Menu;
import com.limvik.view.View;

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

    public static Menu getMenuInput (View view, Menu[] menus) {

        // 유효성 검사용 정규표현식 설정
        Pattern pattern = Pattern.compile(menus[0].getMenuRegex());
        // 입력 및 유효성 검사
        Scanner scanner = getInstance().getScanner();
        while (!scanner.hasNext(pattern)) {
            // 오류 안내 메시지 출력
            view.printError();
            // 잘못된 입력 버퍼에서 제거
            scanner.nextLine();
        }
        
        // 입력받은 메뉴 번호 저장
        int menuNum = scanner.nextInt();
        // 개행문자 제거
        scanner.nextLine();

        return menus[menuNum - 1];
    }

    public static String getUserNameInput () {
        
        // 입력 및 유효성 검사
        Scanner scanner = getInstance().getScanner();
        // 입력받은 이름 저장
        String name = scanner.nextLine();
        if (name.length() > 70) name = name.substring(0, 70);
        else if (name.length() == 0) name = "Anonymous";

        return name;
    }

    public static boolean isYesOrNo() {
        Scanner scanner = getInstance().getScanner();
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")) {
                return input.equalsIgnoreCase("y");
            }
        }
    }
}
