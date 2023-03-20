package hello.board.domain.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.controller.post.dto.req.PostSearchCondition;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.controller.post.dto.res.QPostResDto;
import hello.board.domain.comment.entity.QComment;
import hello.board.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static hello.board.domain.member.entity.QMember.*;
import static hello.board.domain.post.entity.QPost.*;
import static org.springframework.util.StringUtils.hasText;

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

    @Override
    public Page<PostResDto> search(PostSearchCondition condition, Pageable pageable) {
        List<PostResDto> result = queryFactory
                .select(new QPostResDto(
                        post
                ))
                .from(post)
                .leftJoin(post.member, member)
                .where(
                        checkTitle(condition.getTitle()),
                        checkContent(condition.getContent()),
                        checkWriter(condition.getWriter())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    private BooleanExpression checkTitle(String title) {
        return hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression checkContent(String content) {
        return hasText(content) ? post.content.contains(content) : null;
    }

    private BooleanExpression checkWriter(String writer) {
        return hasText(writer) ? post.member.name.eq(writer) : null;
    }
}
