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

    private final EntityManager em; // JPA의 EntityManager

    public JpaMemberRepositoryV1(EntityManager em) {
        this.em = em; // EntityManager 주입
    }

    @Override
    public Member save(Member member) {
        // 회원 정보 저장
        em.persist(member); // member 객체 저장
        return member; // 저장된 member 객체 반환
    }

    @Override
    public Optional<Member> findById(Long id) {
        // ID로 회원 찾기
        Member member = em.find(Member.class, id); // EntityManager의 find 메소드를 사용하여 ID로 회원 조회
        return Optional.ofNullable(member); // 조회된 회원을 Optional 객체로 반환
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        // 이메일로 회원 찾기
        List<Member> result = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList(); // JPQL을 사용하여 이메일로 회원 조회
        return result.stream().findFirst(); // 조회된 회원 목록에서 첫 번째 회원을 Optional 객체로 반환
    }

    @Override
    public void editPassword(Long id, String password) {
        // 회원 비밀번호 변경
        Member member = em.find(Member.class, id); // ID로 회원 조회
        if (member != null) {
            member.setPassword(password); // 조회된 회원의 비밀번호 변경
        }
    }
}