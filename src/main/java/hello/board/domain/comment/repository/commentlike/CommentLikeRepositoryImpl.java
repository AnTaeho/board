package hello.board.domain.comment.repository.commentlike;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.domain.comment.entity.CommentLike;

import javax.persistence.EntityManager;

import static hello.board.domain.comment.entity.QCommentLike.*;

public class CommentLikeRepositoryImpl implements CommentLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommentLikeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public CommentLike haveNoLike(Long commentId, Long memberId) {
        return queryFactory
                .select(commentLike)
                .from(commentLike)
                .where(
                        commentLike.comment.id.eq(commentId),
                        commentLike.member.id.eq(memberId)
                )
                .fetchOne();
    }

}
