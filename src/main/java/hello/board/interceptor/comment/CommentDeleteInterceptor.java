package hello.board.interceptor.comment;

import hello.board.controller.member.session.SessionConst;
import hello.board.domain.comment.entity.Comment;
import hello.board.domain.comment.service.CommentService;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequiredArgsConstructor
public class CommentDeleteInterceptor implements HandlerInterceptor {

    private final CommentService commentService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Member loginMember = findLoginMember(request);
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long id = Long.parseLong((String)pathVariables.get("id"));
        Comment comment = commentService.findComment(id);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return loginMember.getId().equals(comment.getPost().getMember().getId()) || loginMember.getRole().equals(MemberRole.ADMIN);
    }

    private Member findLoginMember(HttpServletRequest request) {
        return (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
