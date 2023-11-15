package baeksproject.project.login.repository;

import baeksproject.project.login.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MemberRespository {
    Member save(Member member); // 저장

    static Optional<Member> findById(Long id) //시스템 id로 찾기
    {
        return null;
    }


    Optional<Member> findByLoginId(String loginId); //로그인 id로 찾기
    List<Member> findAll(); // 모든 멤버 list로 반환
    void updateMember(Long id,Member updateParam);//멤버 수정
    void editPassword(Long id, String password);
}
