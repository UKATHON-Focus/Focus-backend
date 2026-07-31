package com.focus.global.config;

import com.focus.domain.User;
import com.focus.repository.CompanyJDRepository;
import com.focus.repository.UserRepository;
import com.focus.domain.CompanyJD;
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
        userRepository.save(new User("김이화"));

        // 카카오 공고 데이터 (ID: 1)
        companyJDRepository.save(CompanyJD.builder()
                .companyName("카카오")
                .targetAffiliate("카카오")
                .jobName("LLM Research Engineer (Pre-training)")
                .requirements("Java/Kotlin 및 Spring Boot 기반 백엔드 시스템 개발 경험, 대규모 트래픽 및 대용량 데이터 처리/분산 시스템 설계 경험, RESTful API 설계 및 RDBMS/NoSQL 운용 역량")
                .preferredSkills("Kubernetes/Docker 등 컨테이너 기반 인프라 운영 및 AWS/GCP 클라우드 경험, Kafka/RabbitMQ 등 메시지 큐 기반 비동기 시스템 구축 경험, MSA 아키텍처 설계 및 성능 최적화 경험")
                .coreValue("자기주도적 몰입과 끝없는 고민, 솔직하고 수평적인 소통과 공유, 신뢰 기반의 협업과 상호 존중")
                .build());

        // 토스 공고 데이터 (ID: 2)
        companyJDRepository.save(CompanyJD.builder()
                .companyName("토스")
                .targetAffiliate("토스뱅크")
                .jobName("Data Engineer")
                .requirements("Hadoop 및 Kubernetes(EKS/GKE) 기반 대규모 데이터 인프라 운영 경험, 하이브리드/클라우드(AWS/GCP) 데이터 아키텍처 설계 및 전환 경험, Trino/Impala/Spark SQL 분산 쿼리 엔진 튜닝 역량")
                .preferredSkills("EMR/Dataproc/BigQuery 등 클라우드 데이터 서비스 활용 경험, Airflow/Spark/Trino 등 오픈소스 코드 디버깅 및 커스텀 능력, Kafka 기반 실시간 스트리밍 파이프라인 및 데이터 품질 모니터링 구축 경험")
                .coreValue("주도적인 문제 해결 및 완결, 최고 수준의 기술적 탁월함 pursuit, 자율과 책임 기반의 투명한 커뮤니케이션")
                .build());
    }
}
