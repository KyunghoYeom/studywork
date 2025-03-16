package remind.inflearn.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import remind.inflearn.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
}
