package baeksproject.project.login.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 처리 전에 호출됩니다.
        String requestURI = request.getRequestURI(); // 요청 URI 획득
        String uuid = UUID.randomUUID().toString(); // 요청마다 고유한 ID 생성

        request.setAttribute(LOG_ID, uuid); // 요청에 고유 ID를 속성으로 추가

        if (handler instanceof HandlerMethod) {
            // 핸들러가 HandlerMethod 인스턴스인 경우, 추가 처리 가능
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler); // 요청 로깅
        return true; // 다음 인터셉터 또는 컨트롤러로 요청을 계속 전달
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러가 요청을 처리한 후, 뷰를 렌더링하기 전에 호출됩니다.
        log.info("postHandle [{}]", modelAndView); // postHandle 단계에서의 모델 및 뷰 정보 로깅
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 요청 처리가 완전히 끝난 후에 호출됩니다.
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler); // 응답 로깅
        if (ex != null) {
            log.error("afterCompletion error!!", ex); // 예외 발생 시 로깅
        }
    }
}