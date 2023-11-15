package baeksproject.project.login.repository;

import baeksproject.project.login.domain.member.Member;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }



    @Override
    public Optional<Member> findByLoginId(String email) {
        List<Member> result = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        return result.stream().findFirst();
    }


    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }


    @Override
    public void updateMember(Long id, Member updateParam) {
        Member findMember = em.find(Member.class, id);
        findMember.setEmail(updateParam.getEmail());
        findMember.setPassword(updateParam.getPassword());
        findMember.setName(updateParam.getName());
    }

    @Override
    public void editPassword(Long id, String password) {
        Member member = em.find(Member.class, id);
        if (member != null) {
            member.setPassword(password);
        }
    }



}
