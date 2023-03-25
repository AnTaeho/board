package hello.board.domain.member.repository.follow;

import hello.board.domain.member.entity.Follow;
import hello.board.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {

    @Modifying(clearAutomatically = true)
    void deleteByFromMemberAndToMember(Member fromMember, Member toMember);

}
