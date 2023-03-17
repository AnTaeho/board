package hello.board.domain.member.repository.member;

import hello.board.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> findMemberWithAllInfo(Long memberId);
}
