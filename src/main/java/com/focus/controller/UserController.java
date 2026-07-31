package com.focus.controller;

import com.focus.dto.request.UserProfileRequest;
import com.focus.global.response.ApiResponse;
import com.focus.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // [사용자 기본 정보 입력]
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveProfile(@Valid @RequestBody UserProfileRequest request) {
        userService.saveUserProfile(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.successWithMessage("사용자 프로필 정보가 성공적으로 저장되었습니다."));
    }
}