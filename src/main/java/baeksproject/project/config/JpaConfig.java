package baeksproject.project.config;

import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.item.repository.JpaItemRepositoryV1;
import baeksproject.project.item.service.ItemService;
import baeksproject.project.item.service.ItemServiceV1;
import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import baeksproject.project.login.repository.MemberRespository;
import baeksproject.project.login.service.login.SignupService;
import baeksproject.project.login.service.login.SignupServiceV1;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {

    private final EntityManager em;


    public JpaConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public SignupService signupService() {
        return new SignupServiceV1(memberRespository());
    }

    @Bean
    public MemberRespository memberRespository() {
        return new JpaMemberRepositoryV1(em);
    }

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }
    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV1(em);
    }
}
