package baeksproject.project.config;

import baeksproject.project.login.web.argumentresolver.LoginMemberArgumentResolver;
import baeksproject.project.login.web.interceptor.LogInterceptor;
import baeksproject.project.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 핸들러 메소드 인자 추가
        resolvers.add(new LoginMemberArgumentResolver()); // 로그인 멤버 인자 추가
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 인터셉터 추가
        registry.addInterceptor(new LogInterceptor()) // 로그 인터셉터 추가
                .order(1) // 인터셉터 순서 지정
                .addPathPatterns("/**") // 모든 경로에 적용
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/img/**", "/images/**", "/resources/**"); // 제외할 경로 지정

        registry.addInterceptor(new LoginCheckInterceptor()) // 로그인 체크 인터셉터 추가
                .order(2) // 인터셉터 순서 지정
                .addPathPatterns("/**") // 모든 경로에 적용
                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error", "/img/**", "/images/**", "/resources/**"); // 제외할 경로 지정
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스 핸들러 추가
        registry
                .addResourceHandler("/images/**") // URL 패턴 지정
                .addResourceLocations("file:/home/baek/Downloads/project/src/main/resources/static/images/") // 정적 리소스 위치 지정
                .setCachePeriod(3600) // 캐시 기간 설정
                .resourceChain(true) // 리소스 체인 활성화
                .addResolver(new PathResourceResolver()); // 경로 리소스 추가
    }

    @Bean
    public MessageSource messageSource() {
        // 메시지 소스 빈 등록
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages", "errors"); // 메시지 소스 파일 지정
        messageSource.setDefaultEncoding("utf-8"); // 기본 인코딩 설정
        return messageSource;
    }

}