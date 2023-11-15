package baeksproject.project.login.domain.member;

import baeksproject.project.item.domain.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@Entity
public class Member implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>();

    public Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Member() {

    }

}