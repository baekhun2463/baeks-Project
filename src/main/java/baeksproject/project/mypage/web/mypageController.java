package baeksproject.project.mypage.web;

import baeksproject.project.item.domain.Item;
import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.web.argumentresolver.Login;
import baeksproject.project.mypage.service.MypageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class mypageController {

    private final MypageService mypageService;

    @GetMapping("/mypage")
    public String myPage(@Login Member loginMember, Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute("memberId");
        if (loginMember == null) {
            return "home";
        }

        // 사용자가 올린 아이템을 찾아 모델에 추가
        List<Item> myItems = mypageService.findMyItem(memberId);
        model.addAttribute("member", loginMember);
        model.addAttribute("items", myItems); // 사용자의 아이템 목록을 모델에 추가

        return "mypage/mypage";
    }
}

