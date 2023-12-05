package baeksproject.project.login.repository;

import baeksproject.project.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
@RequiredArgsConstructor
public class JpaMemberRepositoryV2 implements MemberRespository{

    private final SpringDataJpaMemberRepository repository; // Spring Data JPA 리포지토리

    @Override
    public Member save(Member member) {
        // 회원 정보 저장
        return repository.save(member); // Spring Data JPA의 save 메소드를 사용하여 회원 정보 저장
    }

    @Override
    public Optional<Member> findById(Long id) {
        // ID로 회원 찾기
        return repository.findById(id); // Spring Data JPA의 findById 메소드를 사용하여 ID로 회원 조회
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        // 이메일로 회원 찾기
        return repository.findByEmail(email); // 사용자 정의 메소드 findByEmail을 사용하여 이메일로 회원 조회
    }

    @Override
    public void editPassword(Long id, String password) {
        // 회원 비밀번호 변경
        Member findMember = repository.findById(id).orElseThrow(); // ID로 회원 조회, 없으면 예외 발생
        findMember.setPassword(password); // 조회된 회원의 비밀번호 변경
    }
}

