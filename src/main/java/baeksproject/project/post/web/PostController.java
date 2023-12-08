package baeksproject.project.post.web;

import baeksproject.project.login.web.login.LoginForm;
import baeksproject.project.post.domain.Post;
import baeksproject.project.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public String list(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "post/posts";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "post/post";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("postForm") PostForm postForm) {
        return "post/addForm";
    }

    @PostMapping("/add")
    public String addPost(@Valid @ModelAttribute PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/addForm";
        }

        Post newPost = new Post();
        newPost.setTitle(postForm.getTitle());
        newPost.setContent(postForm.getContent());
        // 필요한 경우, 추가적인 필드 설정
        postService.save(newPost);

        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            // 게시물이 존재하지 않는 경우의 처리
            // 예: 에러 메시지를 표시하고 게시물 목록으로 리다이렉트
            return "redirect:/posts";
        }
        PostForm postForm = new PostForm(post.getTitle(), post.getContent(), post.getAuthor());
        model.addAttribute("postForm", postForm);
        return "post/editForm";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, @Valid @ModelAttribute PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/editForm";
        }

        Post existingPost = postService.findById(id);
        existingPost.setTitle(postForm.getTitle());
        existingPost.setContent(postForm.getContent());
        // 필요한 경우, 추가적인 필드 업데이트
        postService.save(existingPost);

        return "redirect:/post/" + id;
    }
}
