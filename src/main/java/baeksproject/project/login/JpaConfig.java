package baeksproject.project.login;

import baeksproject.project.login.service.login.LoginService;
import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import baeksproject.project.login.repository.MemberRespository;
import baeksproject.project.login.service.login.SignupService;
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
        return new SignupService(memberRespository);
    }

    @Bean
    public MemberRespository memberRespository() {
        return new JpaMemberRepositoryV1(em);
    }
}
