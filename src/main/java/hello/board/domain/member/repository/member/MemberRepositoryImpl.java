package hello.board.domain.member.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.domain.member.entity.Member;

import javax.persistence.EntityManager;
import java.util.Optional;

import static hello.board.domain.member.entity.QMember.*;
import static hello.board.domain.post.entity.QPost.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //페치 조인을 여러개 사용하면 예외가 발생합니다.
    //가장 자식이 많은 것을 페치 조인해주고
    //나머지는 배치사이즈 설정을 통해서 최대한 성능을 올려준다
    @Override
    public Optional<Member> findMemberWithAllInfo(Long memberId) {

        Member findMember = queryFactory
                .select(member)
                .from(member)
                .join(member.posts, post).fetchJoin()
//                .join(post.comments, comment).fetchJoin()
//                .join(member.notifications, notification).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(findMember);
    }

}
