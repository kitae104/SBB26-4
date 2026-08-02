package inhatc.aic.sbb.member.service;

import inhatc.aic.sbb.member.dto.MemberDto;
import inhatc.aic.sbb.member.entity.Member;
import inhatc.aic.sbb.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public void createMember(@Valid MemberDto memberDto) {
        Member member = Member.builder()
                .username(memberDto.getUsername())
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword1()))
                .gender(memberDto.getGender())
                .department(memberDto.getDepartment())
                .registration(memberDto.getRegistration())
                .build();
        memberRepository.save(member);
    }

    public Member getMember(String name) {
        Member member = memberRepository.findByUsername(name).orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        return member;
    }
}
