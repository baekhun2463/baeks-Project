package baeksproject.project.login.repository;

import baeksproject.project.login.domain.member.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MemberRespository {
    Member save(Member member);

    //public Member findById(Long id);

    public Optional<Member> findByEmail(String email);

    public Optional<Member> findByPassword(String password);

    //public Optional<Member> findByLoginId(String loginId);

    //public void delete();
}
