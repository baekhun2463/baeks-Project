package baeksproject.project.post.domain;

import baeksproject.project.login.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String author;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @ManyToOne // 다대일 관계를 나타내는 어노테이션
    @JoinColumn(name = "member_id") // 외래키를 지정하는 어노테이션
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }


    public Post() {
    }

}