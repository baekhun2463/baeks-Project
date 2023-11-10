package baeksproject.project.login.web.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LoginForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    LoginForm(String email, String password){
        this.email = email;
        this.password = password;
    }
}
