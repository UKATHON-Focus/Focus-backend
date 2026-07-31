package com.focus.domain;

public enum ApplicationStatus {
    PENDING,    // 대기 중
    ANALYZING,  // 분석 중
    GENERATING, // 생성 중
    COMPLETED,  // 완료
    FAILED      // 에러 발생 시
}
