package baeksproject.project.post.web;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PostForm {

        private Long id;

        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        private String author;

        @Column(updatable = false)
        private LocalDateTime createdDate;

        @PrePersist
        protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

        PostForm(String title, String content, String author){
            this.title = title;
            this.content = content;
            this.author = author;
        }

        PostForm(){}
}


