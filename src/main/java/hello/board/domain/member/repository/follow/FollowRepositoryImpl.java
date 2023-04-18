package hello.board.domain.member.repository.follow;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.domain.member.entity.Follow;
import hello.board.domain.member.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hello.board.domain.member.entity.QFollow.*;
import static hello.board.domain.member.entity.QMember.*;

public class FollowRepositoryImpl implements FollowRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public FollowRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Member> findAllByToMember(Member toMember) {
        JPAQuery<Follow> result = queryFactory
                .selectFrom(follow)
                .where(follow.toMember.id.eq(toMember.getId()))
                .fetchAll();

        return result.stream()
                .map(Follow::getFromMember)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAlreadyFollow(Member toMember, Member fromMember) {
        Follow findFollow = queryFactory
                .selectFrom(follow)
                .join(follow.toMember, member).fetchJoin()
                .join(follow.fromMember, member).fetchJoin()
                .where(follow.toMember.id.eq(toMember.getId()), follow.fromMember.id.eq(fromMember.getId()))
                .fetchOne();
        return findFollow != null;
    }

    @Override
    public Optional<Double> findAverageFollowerAge(Member toMember) {
        Double ageAverage = queryFactory
                .select(
                        follow.fromMember.age.avg()
                )
                .from(follow)
                .where(follow.toMember.id.eq(toMember.getId()))
                .fetchOne();
        return Optional.ofNullable(ageAverage);
    }
}
