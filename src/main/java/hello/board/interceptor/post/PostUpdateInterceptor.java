package hello.board.interceptor.post;

import hello.board.controller.member.session.SessionConst;
import hello.board.domain.member.entity.Member;
import hello.board.domain.post.entity.Post;
import hello.board.domain.post.repository.PostRepository;
import hello.board.exception.unauthorized.UpdateUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequiredArgsConstructor
public class PostUpdateInterceptor implements HandlerInterceptor {

    private final PostRepository postRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Member loginMember = findLoginMember(request);
        Long id = findIdFromPathVariables(request);
        Post post = postRepository.findPost(id);
        if (isMyPost(loginMember, post)) {
            return true;
        }
        throw new UpdateUnauthorizedException();
    }

    private Member findLoginMember(HttpServletRequest request) {
        return (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
    }

    private Long findIdFromPathVariables(HttpServletRequest request) {
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return Long.parseLong((String)pathVariables.get("postId"));
    }

    private boolean isMyPost(Member loginMember, Post post) {
        return post.getMember().getId().equals(loginMember.getId());
    }
}
