package hello.board.interceptor;

import hello.board.exception.unauthorized.AdminUnauthorizedException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("admin") == null) {
            throw new AdminUnauthorizedException();
        }
        return true;
    }
}
