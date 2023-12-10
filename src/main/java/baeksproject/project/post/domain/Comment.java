package baeksproject.project.post.domain;

import baeksproject.project.login.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter @Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String author;

    private String content;

    private LocalDateTime createdDate;

    public Comment() {

    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }


}
