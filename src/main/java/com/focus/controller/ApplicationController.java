package com.focus.controller;

import com.focus.dto.ApiResponse;
import com.focus.dto.ApplicationDetailResponseDto;
import com.focus.dto.ApplicationGenerateRequestDto;
import com.focus.dto.ApplicationGenerateResponseDto;
import com.focus.dto.ApplicationListResponseDto;
import com.focus.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    // TODO: 인증 붙기 전까지 데모용 하드코딩
    private static final Long TEMP_USER_ID = 1L;

    @PostMapping("/api/applications/generate")
    public ResponseEntity<ApiResponse<ApplicationGenerateResponseDto>> generate(
            @RequestBody ApplicationGenerateRequestDto request) {

        ApplicationGenerateResponseDto result =
                applicationService.generateApplication(TEMP_USER_ID, request.getJdId());

        ApiResponse<ApplicationGenerateResponseDto> response = ApiResponse.success(
                HttpStatus.CREATED.value(),
                "AI 맞춤형 자소서 생성을 시작합니다.",
                result
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/applications")
    public ResponseEntity<ApiResponse<List<ApplicationListResponseDto>>> getApplicationList() {

        List<ApplicationListResponseDto> result =
                applicationService.getApplicationList(TEMP_USER_ID);

        ApiResponse<List<ApplicationListResponseDto>> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "요청에 성공하였습니다.",
                result
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/applications/{applicationId}")
    public ResponseEntity<ApiResponse<ApplicationDetailResponseDto>> getApplicationDetail(
            @PathVariable Long applicationId) {

        ApplicationDetailResponseDto result =
                applicationService.getApplicationDetail(applicationId);

        ApiResponse<ApplicationDetailResponseDto> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "요청에 성공하였습니다.",
                result
        );

        return ResponseEntity.ok(response);
    }
}