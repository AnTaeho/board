package hello.board.interceptor.comment;

import hello.board.controller.member.session.SessionConst;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.service.CommentService;
import hello.board.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequiredArgsConstructor
public class CommentUpdateInterceptor implements HandlerInterceptor {

    private final CommentService commentService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Member loginMember = findLoginMember(request);
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long id = Long.parseLong((String)pathVariables.get("commentId"));
        Comment comment = commentService.findComment(id);

        return comment.getPost().getMember().getId().equals(loginMember.getId());
    }

    private Member findLoginMember(HttpServletRequest request) {
        return (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
