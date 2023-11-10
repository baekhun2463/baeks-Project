package baeksproject.project.login.web;


import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.domain.member.MemberRepositoryV1;
import baeksproject.project.login.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepositoryV1 memberRepositoryV1;
    //private final SessionManager sessionManager;


    @GetMapping("/")
    public String homeLogin(@Login Member loginMember, Model model) {

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
