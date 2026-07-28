package inhatc.aic.sbb.member.entity;

import inhatc.aic.sbb.member.constant.Department;
import inhatc.aic.sbb.member.constant.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;                // 이름

    private String password;                // 비밀번호

    @Column(unique = true, nullable = false)
    private String email;                   // 이메일

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;                  // 성별

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Department department;          // 학과

    @Column(nullable = false)
    private Boolean registration;           // 등록 확인

    @CreatedDate
    private LocalDateTime created;          // 생성일

}