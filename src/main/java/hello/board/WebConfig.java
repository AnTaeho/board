package hello.board;

import hello.board.argumentresolver.LoginMemberArgumentResolver;
import hello.board.domain.comment.service.CommentService;
import hello.board.domain.post.service.PostService;
import hello.board.filter.LoginCheckFilter;
import hello.board.interceptor.AdminCheckInterceptor;
import hello.board.interceptor.comment.CommentDeleteInterceptor;
import hello.board.interceptor.member.MemberDeleteInterceptor;
import hello.board.interceptor.post.PostDeleteInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final PostService postService;
    private final CommentService commentService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminCheckInterceptor())
                .order(1)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new MemberDeleteInterceptor())
                .order(2)
                .addPathPatterns("/member/delete/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new PostDeleteInterceptor(postService))
                .order(3)
                .addPathPatterns("/posts/delete/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new CommentDeleteInterceptor(commentService))
                .order(4)
                .addPathPatterns("/comment/delete/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
