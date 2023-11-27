package baeksproject.project.login.service.login;

import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.JpaMemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JpaMemberRepositoryV2 jpaMemberRepositoryV2;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Member login(String email, String Password) {
        return jpaMemberRepositoryV2.findByEmail(email)
                .filter(m -> passwordEncoder.matches(Password, m.getPassword()))
                .orElse(null);
    }
}
