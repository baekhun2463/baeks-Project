package baeksproject.project.config;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {

    private final EntityManager em;


    public JpaConfig(EntityManager em) {
        this.em = em;
    }

//    @Bean
//    public SignupService signupService() {
//        return new SignupServiceV1(memberRespository());
//    }
//
//    @Bean
//    public MemberRespository memberRespository() {
//        return new JpaMemberRepositoryV1(em);
//    }
//
//    @Bean
//    public ItemService itemService() {
//        return new ItemServiceV1(itemRepository(), memberRespository());
//    }
//    @Bean
//    public ItemRepository itemRepository() {
//        return new JpaItemRepositoryV1(em);
//    }
}
