package hello.board.domain.member.repository.member;

import hello.board.domain.member.entity.Member;
import hello.board.exception.notfound.MemberNotFoundException;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    @EntityGraph(attributePaths = {"posts"})
    Optional<Member> findMemberById(Long id);

    default Member findMember(Long memberId) {
        return findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    default Member findMemberWithAllInfo(Long memberId) {
        return findMemberById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
