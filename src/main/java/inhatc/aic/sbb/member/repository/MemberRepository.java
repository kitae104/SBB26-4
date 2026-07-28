package inhatc.aic.sbb.member.repository;

import inhatc.aic.sbb.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
