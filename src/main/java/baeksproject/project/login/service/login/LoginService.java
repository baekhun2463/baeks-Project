package baeksproject.project.login.service.login;

import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import baeksproject.project.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JpaMemberRepositoryV1 jpaMemberRepositoryV1;

    /**
     * @return null 로그인 실패
     */
    public Member login(String email, String password) {
        return jpaMemberRepositoryV1.findByLoginId(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
