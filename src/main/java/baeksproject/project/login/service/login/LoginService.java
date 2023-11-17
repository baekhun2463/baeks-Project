package baeksproject.project.login.service.login;

import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.JpaMemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JpaMemberRepositoryV2 jpaMemberRepositoryV2;

    /**
     * @return null 로그인 실패
     */
    public Member login(String email, String password) {
        return jpaMemberRepositoryV2.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
