package com.focus.controller;

import com.focus.dto.JdsResponseDto;
import com.focus.service.JdsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JdsController {

    private final JdsService companyJDService;

    @GetMapping("/api/jds")
    public ResponseEntity<JdsResponseDto> getJobDropdownList() {
        return ResponseEntity.ok(companyJDService.getJobDropdownList());
    }
}
