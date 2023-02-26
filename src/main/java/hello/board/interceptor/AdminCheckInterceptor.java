package hello.board.interceptor;

import hello.board.entity.member.MemberRole;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute(MemberRole.ADMIN.getDescription()) == null) {
            response.sendRedirect("/home");
            return false;
        }
        return true;
    }
}
