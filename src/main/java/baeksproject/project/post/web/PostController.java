package baeksproject.project.post.web;

import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.MemberRespository;
import baeksproject.project.login.web.login.LoginForm;
import baeksproject.project.post.domain.Comment;
import baeksproject.project.post.domain.Post;
import baeksproject.project.post.repository.PostUpdateDto;
import baeksproject.project.post.service.CommentService;
import baeksproject.project.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final MemberRespository memberRespository;


    @GetMapping
    public String posts(@RequestParam(required = false) String keyword,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "5") int size,
                        Model model) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Post> postPage;
        if (keyword != null && !keyword.isEmpty()) {
            // 검색 및 페이징 로직 구현 (검색 기능이 필요한 경우)
            postPage = postService.searchPosts(keyword, pageable);
        } else {
            postPage = postService.findPosts(pageable);
        }

        model.addAttribute("posts", postPage.getContent()); // 페이지에 해당하는 게시글 목록
        model.addAttribute("page", postPage); // 페이지 정보 (Page 객체)

        return "post/posts";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "post/post";
    }



    @GetMapping("/add")
    public String addForm(@ModelAttribute("postForm") PostForm postForm, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 현재 세션 가져오기
        if (session != null) {
            Long memberId = (Long) session.getAttribute("memberId"); // 세션에서 회원 ID 가져오기
            if (memberId != null) {
                model.addAttribute("memberId", memberId); // 모델에 회원 ID 추가
            }
        }
        return "post/addForm";
    }


    @PostMapping("/add")
    public String addPost(@Valid @ModelAttribute PostForm postForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "post/addForm";
        }

        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute("memberId");
        Member member = memberRespository.findById(memberId).orElse(null);

        if (member == null) {
            // 멤버가 존재하지 않는 경우의 처리 (예: 로그인 페이지로 리다이렉트)
            return "redirect:/login";
        }

        Post newPost = new Post();
        newPost.setTitle(postForm.getTitle());
        newPost.setContent(postForm.getContent());
        newPost.setAuthor(member.getName());
        postService.save(newPost, memberId);

        return "redirect:/posts";
    }


    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            // 게시물이 존재하지 않는 경우의 처리
            return "redirect:/posts";
        }
        PostForm postForm = new PostForm();
        postForm.setId(post.getId()); // PostForm에 id 설정
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        postForm.setAuthor(post.getAuthor());
        model.addAttribute("postForm", postForm);
        return "post/editForm";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, @Valid @ModelAttribute PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/editForm";
        }

        PostUpdateDto updateDto = new PostUpdateDto();
        updateDto.setTitle(postForm.getTitle());
        updateDto.setContent(postForm.getContent());
        // 필요한 경우, 추가적인 필드 업데이트

        try {
            postService.update(id, updateDto);
        } catch (RuntimeException e) {
            // 게시물이 존재하지 않거나 업데이트 중 오류 발생 시의 처리
            return "redirect:/posts";
        }

        return "redirect:/posts/" + id; // URL 수정
    }

    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }


}
