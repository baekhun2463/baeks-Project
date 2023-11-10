package baeksproject.project.login.repository;

import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.MemberRespository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
@Transactional
public class JpaMemberRepositoryV1 implements MemberRespository {

    private final EntityManager em;

    public JpaMemberRepositoryV1(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByPassword(String password) {
        return Optional.empty();
    }


}
