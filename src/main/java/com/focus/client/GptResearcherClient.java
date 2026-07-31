package com.focus.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
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
        Map<String, Object> requestBody = Map.of(
                "task", prompt,
                "report_type", "research_report"
        );

        return webClient.post()
                .uri("/research")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
