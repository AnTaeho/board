package hello.board.domain.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.domain.member.entity.QMember;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.entity.QPost;

import javax.persistence.EntityManager;
import java.util.Optional;

import static hello.board.domain.member.entity.QMember.*;
import static hello.board.domain.post.entity.QPost.*;

public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Post> findByIdWithFetchJoinMember(Long postId) {
        Post post = queryFactory
                .selectFrom(QPost.post)
                .join(QPost.post.member, member).fetchJoin()
                .where(QPost.post.id.eq(postId))
                .fetchOne();

        return Optional.ofNullable(post);
    }
}
