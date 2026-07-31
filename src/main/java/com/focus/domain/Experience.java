package com.focus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "experiences")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "question_index", nullable = false)
    private Long questionIndex;

    @Column(name = "answer_content", columnDefinition = "TEXT")
    private String answerContent;

    @Builder
    public Experience(User user, Long questionIndex, String answerContent) {
        this.user = user;
        this.questionIndex = questionIndex;
        this.answerContent = answerContent;
    }

    public void updateAnswer(String answerContent) {
        this.answerContent = answerContent;
    }
}