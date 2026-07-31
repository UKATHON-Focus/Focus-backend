package com.focus.controller;

import com.focus.dto.request.ExperienceRequest;
import com.focus.dto.response.ExperienceResponse;
import com.focus.global.response.ApiResponse;
import com.focus.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @PutMapping("/{questionIndex}")
    public ResponseEntity<ApiResponse<ExperienceResponse>> putExperience(
            @PathVariable Long questionIndex,
            @RequestBody ExperienceRequest request) {

        Long currentUserId = 1L;

        ExperienceResponse response = experienceService.putExperience(currentUserId, questionIndex, request);

        return ResponseEntity.ok(ApiResponse.success(response, "요청에 성공하였습니다."));
    }
}