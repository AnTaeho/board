package hello.board.domain.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.domain.comment.entity.QComment;
import hello.board.domain.post.entity.Post;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static hello.board.domain.member.entity.QMember.*;
import static hello.board.domain.post.entity.QPost.*;

public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Post> findPostWithMemberInfo(Long postId) {
        Post findPost = queryFactory
                .selectFrom(post)
                .join(post.member, member).fetchJoin()
                .where(post.id.eq(postId))
                .fetchOne();

        return Optional.ofNullable(findPost);
    }

    @Override
    public Optional<Post> findPostWithCommentInfo(Long postId) {
        Post findPost = queryFactory
                .selectFrom(post)
                .join(post.comments, QComment.comment).fetchJoin()
                .where(post.id.eq(postId))
                .fetchOne();

        return Optional.ofNullable(findPost);
    }

    @Override
    public List<Post> findPostsOfMember(Long memberId) {
        return queryFactory
                .selectFrom(post)
                .join(post.member, member).fetchJoin()
                .where(member.id.eq(memberId))
                .fetch();

    }
}
