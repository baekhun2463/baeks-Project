package baeksproject.project.post.web;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class postController {

    //private final postService postService; // 게시물 서비스 의존성 주입

    @GetMapping("/posts")
    public String posts(){
        return "post/posts";
    }

    @GetMapping("/posts/add")
    public String addPost(){
        return "post/post";
    }
}
