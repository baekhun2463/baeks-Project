package baeksproject.project.post.web;

import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.MemberRespository;
import baeksproject.project.post.domain.Comment;
import baeksproject.project.post.repository.CommentUpdateDto;
import baeksproject.project.post.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MemberRespository memberRespository;

    @PostMapping
    public String addComment(@PathVariable Long postId, @ModelAttribute Comment comment, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute("memberId");
        Member member = memberRespository.findById(memberId).orElse(null);

        if (member == null) {
            // 멤버가 존재하지 않는 경우의 처리 (예: 로그인 페이지로 리다이렉트)
            return "redirect:/login";
        }
        model.addAttribute("currentMemberId", memberId); // 모델에 회원 ID 추가
        comment.setAuthor(member.getName());
        commentService.saveComment(postId, comment);
        return "redirect:/posts/" + postId;
    }

//    @PostMapping("/{commentId}/edit")
//    public String editComment(@PathVariable Long postId, @PathVariable Long commentId, @ModelAttribute CommentUpdateDto commentUpdateDto) {
//        commentService.editComment(commentId, commentUpdateDto);
//        return "redirect:/posts/" + postId;
//    }


    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId;
    }


}
