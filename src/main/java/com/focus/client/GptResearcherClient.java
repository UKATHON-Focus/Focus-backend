package com.focus.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class GptResearcherClient {

    private final WebClient webClient;

    public GptResearcherClient(@Value("${gpt-researcher.url:http://localhost:8000}") String baseUrl) {
        // GPT 분석 응답 대기 시간을 고려하여 3분(180초) 타임아웃 설정
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(180));

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public String runResearch(String prompt) {
        // OpenAPI ResearchRequest 규격에 맞춘 필수 필드 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("task", prompt);
        requestBody.put("report_type", "research_report");
        requestBody.put("report_source", "web");
        requestBody.put("tone", "Objective");
        requestBody.put("repo_name", "");
        requestBody.put("branch_name", "");
        requestBody.put("generate_in_background", false); // 결과를 즉시 동기 응답으로 받기 위함

        return webClient.post()
                .uri("/report/")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
