package com.focus.service;

import com.focus.domain.BasicSpec;
import com.focus.domain.Certificate;
import com.focus.domain.Internship;
import com.focus.domain.User;
import com.focus.dto.request.UserProfileRequest;
import com.focus.dto.response.UserProfileResponse;
import com.focus.global.exception.CustomException;
import com.focus.global.exception.ErrorCode;
import com.focus.repository.BasicSpecRepository;
import com.focus.repository.CertificateRepository;
import com.focus.repository.InternshipRepository;
import com.focus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BasicSpecRepository basicSpecRepository;
    private final CertificateRepository certificateRepository;
    private final InternshipRepository internshipRepository;

    // [사용자 기본 정보 입력]
    @Transactional
    public void saveUserProfile(UserProfileRequest request) {
        User user = userRepository.save(User.builder().name(request.name()).build());

        // 기본 스펙 저장
        if (request.basicSpec() != null) {
            basicSpecRepository.save(request.basicSpec().toEntity(user));
        }

        // 자격증 저장
        if (request.certificates() != null && !request.certificates().isEmpty()) {
            List<Certificate> certificates = request.certificates().stream()
                    .map(dto -> dto.toEntity(user))
                    .toList();
            certificateRepository.saveAll(certificates);
        }

        // 인턴 경험 저장
        if (request.internships() != null && !request.internships().isEmpty()) {
            List<Internship> internships = request.internships().stream()
                    .map(dto -> dto.toEntity(user))
                    .toList();
            internshipRepository.saveAll(internships);
        }
    }

    // [마이페이지 조회]
    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 연관 데이터 조회
        BasicSpec basicSpec = basicSpecRepository.findByUserId(userId).orElse(null);
        List<Certificate> certificates = certificateRepository.findAllByUserId(userId);
        List<Internship> internships = internshipRepository.findAllByUserId(userId);

        return UserProfileResponse.of(user, basicSpec, certificates, internships);
    }
}