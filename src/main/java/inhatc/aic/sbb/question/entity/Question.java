package inhatc.aic.sbb.question.entity;

import inhatc.aic.sbb.answer.entity.Answer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString // answerList는 제외하고 출력
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)  // 자동으로 정보 입력
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;    // 아이디

    @Column(length = 200, nullable = false)
    private String subject; // 제목

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    @CreatedDate
    private LocalDateTime created;  // 생성일

    // 질문 하나에 여려개의 답변이 있을 수 있음
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answerList;    // 답변 리스트
}
