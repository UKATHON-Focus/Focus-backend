package com.focus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "certificates")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "cert_name", nullable = false)
    private String certName;

    @Builder
    public Certificate(User user, String certName) {
        this.user=user;
        this.certName=certName;
    }

    public void update(String certName) {
        if (certName != null) this.certName = certName;
    }
}
