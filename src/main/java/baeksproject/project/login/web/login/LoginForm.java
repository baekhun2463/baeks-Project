package baeksproject.project.login.web.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LoginForm {

    @NotEmpty @Email  //이메일 형식으로 입력해야한는 유효성 검사 에노테이션
    private String email;

    @NotEmpty
    private String password;

    LoginForm(String email, String password){
        this.email = email;
        this.password = password;
    }
}
