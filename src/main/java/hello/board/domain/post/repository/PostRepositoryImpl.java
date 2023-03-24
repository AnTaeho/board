package hello.board.domain.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.controller.post.dto.req.PostSearchCondition;
import hello.board.controller.post.dto.res.PostResDto;
import hello.board.controller.post.dto.res.QPostResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static hello.board.domain.member.entity.QMember.*;
import static hello.board.domain.post.entity.PostStatus.*;
import static hello.board.domain.post.entity.QPost.*;
import static org.springframework.util.StringUtils.hasText;

public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
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

    @Override
    public Page<PostResDto> findAllPostedPost(Pageable pageable) {
        List<PostResDto> result = queryFactory
                .select(new QPostResDto(
                        post
                ))
                .from(post)
                .where(post.status.eq(POSTED))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<PostResDto> findAllAllWaitingPost(Pageable pageable) {
        List<PostResDto> result = queryFactory
                .select(new QPostResDto(
                        post
                ))
                .from(post)
                .where(post.status.eq(WAITING_TO_POST))
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
