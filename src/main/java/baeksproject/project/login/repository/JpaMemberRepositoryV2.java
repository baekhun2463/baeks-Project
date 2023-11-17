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

    private final SpringDataJpaMemberRepository repository;

    @Override
    public Member save(Member member) {
        return repository.save(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void editPassword(Long id, String password) {
        Member findMember = repository.findById(id).orElseThrow();
        findMember.setPassword(password);
    }
}
