package baeksproject.project.login.web.member;

import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final JpaMemberRepositoryV1 jpaMemberRepositoryV1;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        // 회원 가입 폼을 반환
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 폼 검증 오류가 있을 경우, 회원 가입 폼으로 다시 리턴
            return "members/addMemberForm";
        }

        // 비밀번호 해시 생성기
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 원본 비밀번호
        String originalPassword = member.getPassword();

        // 비밀번호를 해시하여 저장
        String hashedPassword = passwordEncoder.encode(originalPassword);
        member.setPassword(hashedPassword);

        // 회원 정보 저장
        jpaMemberRepositoryV1.save(member);

        // 회원 가입 후 로그인 페이지로 리다이렉트
        return "redirect:/login";
    }
}