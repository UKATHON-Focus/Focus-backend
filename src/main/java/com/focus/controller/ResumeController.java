package com.focus.controller;

import com.focus.dto.ResumeRequestDto;
import com.focus.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeResume(@RequestBody ResumeRequestDto requestDto) {
        String result = resumeService.generateTailoredResume(requestDto);
        return ResponseEntity.ok(result);
    }
}
