package com.limvik;

import com.limvik.view.MainView;
import com.limvik.view.View;

public class SimpleFlashCardsApplication 
{
    public static void main(String[] args)
    {
        // 2초간 인사 메시지 출력
        greetingToUser(2);

    }

    private static void greetingToUser(int seconds) {
        // 인사 메시지 출력
        MainView.printGreeting();
        // 지정된 시간 동안 정지 후 메시지 삭제
        View.pause(seconds);
        View.clearScreen();
    }
}
