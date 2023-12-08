package baeksproject.project.post.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostForm {

        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        private String author;

        PostForm(String title, String content, String author){
            this.title = title;
            this.content = content;
            this.author = author;
        }
}


