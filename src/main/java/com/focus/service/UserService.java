package com.focus.service;

import com.focus.domain.BasicSpec;
import com.focus.domain.Certificate;
import com.focus.domain.Internship;
import com.focus.domain.User;
import com.focus.dto.request.UserProfileRequest;
import com.focus.dto.request.UserProfileUpdateRequest;
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
import java.util.Objects;

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

    // [마이페이지 정보 수정]
    @Transactional
    public void updateUserProfile(Long userId, UserProfileUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (request.name() != null) {
            user.updateName(request.name());
        }

        // 기본 스펙 업데이트
        if (request.basicSpec() != null) {
            basicSpecRepository.findByUserId(userId).ifPresent(spec ->
                    spec.update(
                            request.basicSpec().school(),
                            request.basicSpec().major(),
                            request.basicSpec().desiredJob(),
                            request.basicSpec().languageName(),
                            request.basicSpec().languageScore()
                    )
            );
        }

        // 자격증 덮어쓰기
        if (request.certificates() != null) {
            List<Certificate> existingCerts = certificateRepository.findAllByUserId(userId);
            List<Long> requestedIds = request.certificates().stream()
                    .map(UserProfileUpdateRequest.CertificateUpdateDto::certId)
                    .filter(Objects::nonNull)
                    .toList();

            // DTO 리스트에 없는 기존 자격증은 삭제
            List<Certificate> certsToDelete = existingCerts.stream()
                    .filter(c -> !requestedIds.contains(c.getId()))
                    .toList();
            certificateRepository.deleteAll(certsToDelete);

            // DTO 리스트 돌면서 업데이트 혹은 신규 생성
            for (var dto : request.certificates()) {
                if (dto.certId() != null) {
                    existingCerts.stream()
                            .filter(c -> c.getId().equals(dto.certId()))
                            .findFirst()
                            .ifPresent(c -> c.update(dto.certName()));
                } else {
                    certificateRepository.save(dto.toEntity(user));
                }
            }
        }

        // 4. 인턴십 덮어쓰기
        if (request.internships() != null) {
            List<Internship> existingInterns = internshipRepository.findAllByUserId(userId);
            List<Long> requestedIds = request.internships().stream()
                    .map(UserProfileUpdateRequest.InternshipUpdateDto::internId)
                    .filter(Objects::nonNull)
                    .toList();

            List<Internship> internsToDelete = existingInterns.stream()
                    .filter(i -> !requestedIds.contains(i.getId()))
                    .toList();
            internshipRepository.deleteAll(internsToDelete);

            for (var dto : request.internships()) {
                if (dto.internId() != null) {
                    existingInterns.stream()
                            .filter(i -> i.getId().equals(dto.internId()))
                            .findFirst()
                            .ifPresent(i -> i.update(dto.companyName(), dto.role(), dto.startDate(), dto.endDate()));
                } else {
                    internshipRepository.save(dto.toEntity(user));
                }
            }
        }
    }
}