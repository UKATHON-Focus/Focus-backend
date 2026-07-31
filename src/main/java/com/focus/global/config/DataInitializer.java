package com.focus.global.config;

import com.focus.domain.JobPosting;
import com.focus.domain.User;
import com.focus.repository.CompanyJDRepository;
import com.focus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CompanyJDRepository companyJDRepository;

    @Override
    public void run(String... args) throws Exception {
        // 샘플 회원 저장 (ID: 1)
        userRepository.save(new User("홍길동"));

        // 카카오 공고 데이터 (ID: 1)
        companyJDRepository.save(JobPosting.builder()
                .companyName("카카오")
                .position("백엔드 개발자")
                .requirements("대용량 트래픽 처리 경험, 자기주도적 문제 해결 역량, MSA 기반 백엔드 설계 능숙자")
                .build());

        // 토스 공고 데이터 (ID: 2)
        companyJDRepository.save(JobPosting.builder()
                .companyName("토스")
                .position("백엔드 개발자")
                .requirements("속도와 유연성 중시, 비즈니스 영향력 창출, 대규모 금융 데이터 처리 안정성")
                .build());
    }
}
