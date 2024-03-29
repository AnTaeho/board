package hello.board.interceptor.comment;

import hello.board.controller.member.session.SessionConst;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.repository.comment.CommentRepository;
import hello.board.domain.member.entity.Member;
import hello.board.exception.unauthorized.UpdateUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

        @RequiredArgsConstructor
        public class CommentUpdateInterceptor implements HandlerInterceptor {

            private final CommentRepository commentRepository;

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                Member loginMember = findLoginMember(request);
                Long id = findIdFromPathVariables(request);
                Comment comment = commentRepository.findCommentWithPostInfo(id);
                if (isMyComment(loginMember, comment)) {
                    return true;
                }
                throw new UpdateUnauthorizedException();
            }

            private Member findLoginMember(HttpServletRequest request) {
                return (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
    }

    private Long findIdFromPathVariables(HttpServletRequest request) {
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return Long.parseLong((String)pathVariables.get("commentId"));
    }

    private boolean isMyComment(Member loginMember, Comment comment) {
        return comment.getPost().getMember().getId().equals(loginMember.getId());
    }
}
