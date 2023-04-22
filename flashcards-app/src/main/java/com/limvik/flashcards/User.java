package com.limvik.flashcards;

public class User {

    private int id; // 사용자 식별자
    private String name; // 사용자 이름

    // 사용자 객체 초기화
    public User(int id, String name) {
        
        // 사용자 정보 설정
        this.id = id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
