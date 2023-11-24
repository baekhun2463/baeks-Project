package baeksproject.project.config;

import baeksproject.project.item.repository.SpringDataJpaItemRepository;
import baeksproject.project.login.repository.SpringDataJpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringDataJpaConfig {
    private final SpringDataJpaItemRepository springDataJpaItemRepository;
    private final SpringDataJpaMemberRepository springDataJpaMemberRepository;

//    @Bean
//    public ItemService itemService() {
//        return new ItemServiceV1(itemRepository(), memberRespository());
//    }
//
//    @Bean
//    public ItemRepository itemRepository() {
//        return new JpaItemRepositoryV2(springDataJpaItemRepository, memberRespository());
//    }
//
//    @Bean
//    public SignupService signupService() {
//        return new SignupServiceV1(memberRespository());
//    }
//
//    @Bean
//    public MemberRespository memberRespository() {
//        return new JpaMemberRepositoryV2(springDataJpaMemberRepository);
//    }

}
