package hello.board.domain.member.repository.follow;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.domain.member.entity.Follow;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.QMember;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static hello.board.domain.member.entity.QFollow.*;

public class FollowRepositoryImpl implements FollowRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public FollowRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deleteByToMemberAndFromMember(Member toMember, Member fromMember) {
        queryFactory
                .delete(follow)
                .where(follow.toMember.id.eq(toMember.getId()), follow.fromMember.id.eq(fromMember.getId()))
                .execute();
    }

    @Override
    public List<Member> findAllByToMember(Member toMember) {
        return queryFactory
                .selectFrom(follow)
                .where(follow.toMember.id.eq(toMember.getId()))
                .fetchAll()
                .stream()
                .map(Follow::getFromMember)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAlreadyFollow(Member toMember, Member fromMember) {
        Follow findFollow = queryFactory
                .selectFrom(follow)
                .join(follow.toMember, QMember.member).fetchJoin()
                .join(follow.fromMember, QMember.member).fetchJoin()
                .where(follow.toMember.id.eq(toMember.getId()), follow.fromMember.id.eq(fromMember.getId()))
                .fetchOne();
        return findFollow != null;
    }
}
