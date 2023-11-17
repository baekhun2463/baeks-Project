package baeksproject.project.login.repository;

import baeksproject.project.login.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MemberRespository {
    Member save(Member member); // 저장
    Optional<Member> findById(Long id); //시스템 id로 찾기
    Optional<Member> findByEmail(String email);
    void editPassword(Long id, String password);
}
