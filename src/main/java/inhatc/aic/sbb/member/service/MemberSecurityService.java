package inhatc.aic.sbb.member.service;

import inhatc.aic.sbb.member.constant.MemberRole;
import inhatc.aic.sbb.member.entity.Member;
import inhatc.aic.sbb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));

        // 권한 처리
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue())); // 관리자 권한 부여
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue())); // 일반 사용자 권한 부여
        }

        // org.springframework.security.core.userdetails.User 객체를 생성하여 반환
        // SecurityConfig에 AuthenticationManager 빈 생성 필요!!
        return new User(
                member.getUsername(),
                member.getPassword(),
                authorities
        );  // 권한이 있는 사용자 생성
    }
}
