package com.limvik.controller;

import java.util.Scanner;
import java.util.regex.Pattern;

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

    public static int getMenuInput (View view, String regex) {

        // 유효성 검사용 정규표현식 설정
        Pattern pattern = Pattern.compile(regex);
        // 입력 및 유효성 검사
        Scanner scanner = getInstance().getScanner();
        while (!scanner.hasNext(pattern)) {
            // 오류 안내 메시지 출력
            view.printError();
            // 잘못된 입력 버퍼에서 제거
            scanner.nextLine();
        }
        
        // 입력받은 메뉴 번호 저장
        int menu = scanner.nextInt();
        // 개행문자 제거
        scanner.nextLine();

        return menu;
    }
}
