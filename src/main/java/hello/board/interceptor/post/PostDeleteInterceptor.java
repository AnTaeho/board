package hello.board.interceptor.post;

import hello.board.controller.member.session.SessionConst;
import hello.board.domain.member.entity.Member;
import hello.board.domain.member.entity.MemberRole;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.service.PostService;
import hello.board.exception.unauthorized.DeleteUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequiredArgsConstructor
public class PostDeleteInterceptor implements HandlerInterceptor {

    private final PostService postService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Member loginMember = findLoginMember(request);
        Long id = findIdFromPathVariables(request);
        Post post = postService.findPost(id);
        if (isMyPost(loginMember, post) || isAdmin(loginMember)) {
            return true;
        }
        throw new DeleteUnauthorizedException();
    }

    private Member findLoginMember(HttpServletRequest request) {
        return (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
    }

    private Long findIdFromPathVariables(HttpServletRequest request) {
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return Long.parseLong((String)pathVariables.get("postId"));
    }

    private boolean isMyPost(Member loginMember, Post post) {
        return loginMember.getId().equals(post.getMember().getId());
    }

    private boolean isAdmin(Member loginMember) {
        return loginMember.getRole().equals(MemberRole.ADMIN);
    }
}
