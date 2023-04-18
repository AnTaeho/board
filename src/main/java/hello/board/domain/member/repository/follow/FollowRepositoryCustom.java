package hello.board.domain.member.repository.follow;

import hello.board.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface FollowRepositoryCustom {

    List<Member> findAllByToMember(Member toMember);

    boolean isAlreadyFollow(Member toMember, Member fromMember);

    Optional<Double> findAverageFollowerAge(Member toMember);

}
