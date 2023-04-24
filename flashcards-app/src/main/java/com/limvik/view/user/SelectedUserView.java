package com.limvik.view.user;

import com.limvik.enums.SelectedUserMenu;
import com.limvik.view.View;

public class SelectedUserView implements View{

    private static final String WELCOME = "님의 메뉴입니다. 원하시는 메뉴를 선택해주세요.";
    private static final String MENU_GUIDE = "위 메뉴 중 하나를 입력 후 엔터 키를 눌러주세요. 예) 1\n>";
    private static final String ERROR = "정확한 메뉴 번호를 입력하세요. 예) 1\n>";
    private static final String LOADING = "선택하신 화면으로 이동합니다.";

	@Override
	public void printFirstMessage() {
        System.out.println(WELCOME);
	}

	@Override
	public void printLoading() {
        System.out.println(LOADING);
	}

	@Override
	public void printMenu() {
        int menuNumber = 1;

        for (var selectedUserMenu : SelectedUserMenu.values()) {
            System.out.println(menuNumber + ". " + selectedUserMenu.getDescription());
            ++menuNumber;
        }

        System.out.print(MENU_GUIDE);
	}

	@Override
	public void printError() {
		System.out.print(ERROR);
	}
        
}
