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
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 비밀번호 인코더

    public Member login(String email, String Password) {
        // 로그인 메소드
        return jpaMemberRepositoryV2.findByEmail(email) // 이메일을 기준으로 회원 정보 조회
                .filter(m -> passwordEncoder.matches(Password, m.getPassword())) // 입력된 비밀번호와 저장된 비밀번호 비교
                .orElse(null); // 일치하는 회원이 없으면 null 반환
    }
}
