package hello.board.domain.comment.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.entity.QComment;
import hello.board.domain.member.entity.QMember;
import hello.board.domain.post.entity.QPost;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Comment> findCommentWithMemberInfo(Long commentId) {
        Comment comment = queryFactory
                .selectFrom(QComment.comment)
                .join(QComment.comment.post, QPost.post).fetchJoin()
                .join(QPost.post.member, QMember.member).fetchJoin()
                .where(QComment.comment.id.eq(commentId))
                .fetchOne();

        return Optional.ofNullable(comment);
    }

    @Override
    public Optional<Comment> findCommentWithPostInfo(Long commentId) {
        Comment comment = queryFactory
                .selectFrom(QComment.comment)
                .join(QComment.comment.post, QPost.post).fetchJoin()
                .where(QComment.comment.id.eq(commentId))
                .fetchOne();

        return Optional.ofNullable(comment);
    }

}
