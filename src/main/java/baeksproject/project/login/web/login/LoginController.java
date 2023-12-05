package baeksproject.project.login.web.login;

import baeksproject.project.login.service.login.LoginService;
import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService; // 로그인 서비스 의존성 주입

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        // 로그인 폼 페이지 반환
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            // 폼 검증 오류가 있을 경우, 로그인 폼으로 다시 리턴
            return "login/loginForm";
        }

        // 로그인 시도
        Member loginMember = loginService.login(form.getEmail(), form.getPassword());

        if (loginMember == null) {
            // 로그인 실패 시, 오류 메시지 설정 및 로그인 폼으로 리턴
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        HttpSession session = request.getSession(); // 세션 생성 또는 기존 세션 반환
        session.setAttribute("memberId", loginMember.getId()); // 세션에 회원 ID 저장
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember); // 세션에 로그인 회원 객체 저장

        return "redirect:" + redirectURL; // 지정된 URL로 리다이렉트
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화 (로그아웃 처리)
        }
        return "redirect:/"; // 홈으로 리다이렉트
    }

}