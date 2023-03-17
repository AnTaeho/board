package hello.board.domain.member.repository.follow;

import hello.board.domain.member.entity.Member;

import java.util.List;

public interface FollowRepositoryCustom {

    void deleteByToMemberAndFromMember(Member toMember, Member fromMember);
    List<Member> findAllByToMember(Member toMember);
    boolean isAlreadyFollow(Member toMember, Member fromMember);

}
