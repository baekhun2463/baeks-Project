package baeksproject.project.config;

import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.item.repository.JpaItemRepository;
import baeksproject.project.item.service.ItemService;
import baeksproject.project.item.service.ItemServiceV;
import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import baeksproject.project.login.repository.MemberRespository;
import baeksproject.project.login.service.login.SignupService;
import baeksproject.project.login.service.login.SignupServiceV;
import baeksproject.project.post.repository.CommentRepository;
import baeksproject.project.post.repository.PostRepository;
import baeksproject.project.post.service.CommentService;
import baeksproject.project.post.service.CommentServiceV;
import baeksproject.project.post.service.PostService;
import baeksproject.project.post.service.PostServiceV;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "baeksproject.project")
public class JpaConfig {
    private final EntityManager em;



    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepository(em);
    }

    @Bean
    public SignupService signupService() {
        return new SignupServiceV(memberRespository());
    }

    @Bean
    public MemberRespository memberRespository() {
        return new JpaMemberRepositoryV1(em);
    }

    @Bean
    public ItemService itemService(ItemRepository itemRepository, MemberRespository memberRespository) {
        return new ItemServiceV(itemRepository, memberRespository);
    }


    @Bean
    public PostService postService(MemberRespository memberRespository, PostRepository postRepository) {
        return new PostServiceV(memberRespository, postRepository);
    }

    @Bean
    public CommentService commentService(CommentRepository commentRepository, PostRepository postRepository) {
        return new CommentServiceV(commentRepository, postRepository);
    }
}