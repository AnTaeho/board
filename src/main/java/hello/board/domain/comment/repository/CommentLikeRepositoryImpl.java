package hello.board.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.domain.comment.entity.CommentLike;
import hello.board.domain.comment.entity.QCommentLike;

import javax.persistence.EntityManager;

import static hello.board.domain.comment.entity.QCommentLike.*;
import static hello.board.domain.notification.entity.QNotification.*;

public class CommentLikeRepositoryImpl implements CommentLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommentLikeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public boolean hasNoLike(Long commentId, Long memberId) {
        CommentLike commentLike = queryFactory
                .select(QCommentLike.commentLike)
                .from(QCommentLike.commentLike)
                .where(QCommentLike.commentLike.comment.id.eq(commentId), QCommentLike.commentLike.member.id.eq(memberId))
                .fetchOne();
        return commentLike == null;
    }

    @Override
    public void deleteByCommentIdAndMemberId(Long commentId, Long memberId) {
        queryFactory
                .delete(notification)
                .where(notification.commentLike.id.eq(commentId))
                .execute();

        queryFactory
                .delete(commentLike)
                .where(commentLike.comment.id.eq(commentId), commentLike.member.id.eq(memberId))
                .execute();
    }

}
