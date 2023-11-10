package baeksproject.project.login.service.login;

import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final JpaMemberRepositoryV1 jpaMemberRepositoryV1;

    public Member save(Member member) {
        return jpaMemberRepositoryV1.save(member);
    }

}
