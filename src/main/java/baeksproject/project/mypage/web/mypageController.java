package baeksproject.project.mypage.web;

import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.web.argumentresolver.Login;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class mypageController {

    @GetMapping("/mypage")
    public String myPage(@Login Member loginMember, Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute("memberId");
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "mypage/mypage";
    }
}
