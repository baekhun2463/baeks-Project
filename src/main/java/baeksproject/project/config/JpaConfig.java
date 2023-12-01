package baeksproject.project.config;

import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.item.repository.JpaItemRepositoryV3;
import baeksproject.project.item.service.ItemService;
import baeksproject.project.item.service.ItemServiceV1;
import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import baeksproject.project.login.repository.MemberRespository;
import baeksproject.project.login.service.login.SignupService;
import baeksproject.project.login.service.login.SignupServiceV1;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {
    private final EntityManager em;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository(), memberRespository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV3(em);
    }

    @Bean
    public SignupService signupService() {
        return new SignupServiceV1(memberRespository());
    }

    @Bean
    public MemberRespository memberRespository() {
        return new JpaMemberRepositoryV1(em);
    }
}