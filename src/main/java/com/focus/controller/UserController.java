package com.focus.controller;

import com.focus.dto.request.UserProfileRequest;
import com.focus.dto.request.UserProfileUpdateRequest;
import com.focus.dto.response.UserProfileResponse;
import com.focus.global.response.ApiResponse;
import com.focus.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // [마이페이지 조회]
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserProfileResponse>>> getProfile() {

        // 로그인 없어서 무조건 1번 유저를 조회
        Long currentUserId = 1L;
        UserProfileResponse profileResponse = userService.getUserProfile(currentUserId);

        return ResponseEntity.ok(ApiResponse.success(List.of(profileResponse), "요청에 성공하였습니다."));
    }

    // [마이페이지 정보 수정]
    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> updateProfile(@Valid @RequestBody UserProfileUpdateRequest request) {

        // 로그인 없어서 무조건 1번 유저를 조회
        Long currentUserId = 1L;
        userService.updateUserProfile(currentUserId, request);

        return ResponseEntity.ok(ApiResponse.successWithMessage("사용자 프로필 정보가 성공적으로 수정되었습니다."));
    }
}