package com.limvik.flashcards;

import java.time.LocalDate;
import java.util.List;

public class Planner {

    // 학습일정을 찾아라
    public List<Plan> searchStudyPlan(int userId, LocalDate startDate, LocalDate endDate) {
        // 데이터베이스에서 불러오기
    }

    // 학습일정을 기록하라
    public boolean writePlan(int userId, Plan plan) {
        // 데이터베이스에 저장
    }

}
