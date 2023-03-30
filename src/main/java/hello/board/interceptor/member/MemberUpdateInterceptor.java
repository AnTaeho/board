package hello.board.interceptor.member;

import hello.board.controller.member.session.SessionConst;
import hello.board.domain.member.entity.Member;
import hello.board.exception.unauthorized.UpdateUnauthorizedException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class MemberUpdateInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Member loginMember = findLoginMember(request);
        Long id = findIdFromPathVariables(request);
        if (isMe(loginMember, id)) {
            return true;
        }
        throw new UpdateUnauthorizedException();
    }

    private Member findLoginMember(HttpServletRequest request) {
        return (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
    }

    private Long findIdFromPathVariables(HttpServletRequest request) {
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return Long.parseLong((String)pathVariables.get("memberId"));
    }

    private boolean isMe(Member loginMember, Long id) {
        return loginMember.getId().equals(id);
    }
}
