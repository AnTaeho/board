package hello.board;

import hello.board.argumentresolver.LoginMemberArgumentResolver;
import hello.board.domain.comment.repository.comment.CommentRepository;
import hello.board.domain.post.repository.PostRepository;
import hello.board.filter.LoginCheckFilter;
import hello.board.interceptor.AdminCheckInterceptor;
import hello.board.interceptor.comment.CommentDeleteInterceptor;
import hello.board.interceptor.comment.CommentUpdateInterceptor;
import hello.board.interceptor.member.MemberDeleteInterceptor;
import hello.board.interceptor.member.MemberUpdateInterceptor;
import hello.board.interceptor.post.PostDeleteInterceptor;
import hello.board.interceptor.post.PostUpdateInterceptor;
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

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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

        //삭제 권한 인터셉터
        registry.addInterceptor(new MemberDeleteInterceptor())
                .order(2)
                .addPathPatterns("/member/delete/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new PostDeleteInterceptor(postRepository))
                .order(3)
                .addPathPatterns("/posts/delete/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new CommentDeleteInterceptor(commentRepository))
                .order(4)
                .addPathPatterns("/comment/delete/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        //수정 권한 인터셉터
        registry.addInterceptor(new MemberUpdateInterceptor())
                .order(5)
                .addPathPatterns("/member/edit/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new PostUpdateInterceptor(postRepository))
                .order(6)
                .addPathPatterns("/posts/edit/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new CommentUpdateInterceptor(commentRepository))
                .order(7)
                .addPathPatterns("/comment/edit/**")
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
