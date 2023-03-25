package hello.board.domain.member.repository.member;

import hello.board.domain.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByLoginId(String loginId);

    @EntityGraph(attributePaths = {"posts"})
    Optional<Member> findMemberById(Long id);
}
