package baeksproject.project.config;

import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.item.repository.JpaItemRepository;
import baeksproject.project.item.service.ItemService;
import baeksproject.project.item.service.ItemServiceV;
import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import baeksproject.project.login.repository.MemberRespository;
import baeksproject.project.login.service.login.SignupService;
import baeksproject.project.login.service.login.SignupServiceV;
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
        return new ItemServiceV(itemRepository(), memberRespository());
    }

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
}