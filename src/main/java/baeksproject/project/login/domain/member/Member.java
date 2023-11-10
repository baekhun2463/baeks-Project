package baeksproject.project.login.domain.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Member {

    private Long id;

    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;

    Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}