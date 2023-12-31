package baeksproject.project.login.service.login;

import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupServiceV implements SignupService{
    private final MemberRespository memberRespository;

    public Member save(Member member) {
        return memberRespository.save(member);
    }

}
