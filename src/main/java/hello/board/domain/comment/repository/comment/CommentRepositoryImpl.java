package hello.board.domain.comment.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.domain.comment.entity.Comment;

import javax.persistence.EntityManager;
import java.util.Optional;

import static hello.board.domain.comment.entity.QComment.*;
import static hello.board.domain.member.entity.QMember.*;
import static hello.board.domain.post.entity.QPost.*;

public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Comment> findCommentWithMemberInfo(Long commentId) {
        Comment findComment = queryFactory
                .selectFrom(comment)
                .join(comment.post, post).fetchJoin()
                .join(post.member, member).fetchJoin()
                .where(comment.id.eq(commentId))
                .fetchOne();

        return Optional.ofNullable(findComment);
    }

    @Override
    public Optional<Comment> findCommentWithPostInfo(Long commentId) {
        Comment findComment = queryFactory
                .selectFrom(comment)
                .join(comment.post, post).fetchJoin()
                .where(comment.id.eq(commentId))
                .fetchOne();

        return Optional.ofNullable(findComment);
    }

}
