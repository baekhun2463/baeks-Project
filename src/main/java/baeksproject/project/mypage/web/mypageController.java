package baeksproject.project.mypage.web;

import baeksproject.project.item.domain.Item;
import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.web.argumentresolver.Login;
import baeksproject.project.mypage.service.MypageService;
import baeksproject.project.post.domain.Post;
import baeksproject.project.post.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class mypageController {

    private final MypageService mypageService; // 마이페이지 서비스 의존성 주입
    private final ItemRepository itemRepository;
    private final PostRepository postRepository;

    @GetMapping("/mypage")
    public String myPage(@Login Member loginMember, Model model, HttpServletRequest request,
                         @RequestParam(value = "itemPage", defaultValue = "0") int itemPage,
                         @RequestParam(value = "itemSize", defaultValue = "5") int itemSize,
                         @RequestParam(value = "postPage", defaultValue = "0") int postPage,
                         @RequestParam(value = "postSize", defaultValue = "5") int postSize) {
        // 기존 로직
        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute("memberId");
        if (loginMember == null) {
            return "home"; // 로그인하지 않은 사용자는 홈으로 리다이렉트
        }

        model.addAttribute("member", loginMember);




        // 아이템 페이지네이션 로직
        Pageable itemPageable = PageRequest.of(itemPage, itemSize);
        Page<Item> myItems = itemRepository.findByMemberId(loginMember.getId(), itemPageable);
        model.addAttribute("items", myItems.getContent());
        model.addAttribute("itemPage", myItems);

        // 게시글 페이지네이션 로직
        Pageable postPageable = PageRequest.of(postPage, postSize);
        Page<Post> myPosts = postRepository.findByMemberId(loginMember.getId(), postPageable);
        model.addAttribute("posts", myPosts.getContent());
        model.addAttribute("postPage", myPosts);

        return "mypage/mypage";
    }
}

