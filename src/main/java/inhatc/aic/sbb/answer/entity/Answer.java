package inhatc.aic.sbb.answer.entity;

import inhatc.aic.sbb.question.entity.Question;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(exclude = "question")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id; // 아이디

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    @CreatedDate
    private LocalDateTime created;  // 생성일

    // 여러개의 응답이 하나의 질문과 관계가 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;  // 질문(외래키) - toString에서 순환 참조 발생
}