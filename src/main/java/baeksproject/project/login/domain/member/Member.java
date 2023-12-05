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
@Entity // JPA 엔티티임을 나타내며, 이 클래스의 인스턴스는 데이터베이스의 행에 매핑됩니다.
public class Member implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원의 고유 ID, 데이터베이스에서 자동 생성됩니다.

    @NotEmpty // 값이 비어 있으면 안 됨을 나타내는 검증 어노테이션
    @Email // 이메일 형식을 검증하는 어노테이션
    private String email; // 회원의 이메일

    @NotEmpty // 값이 비어 있으면 안 됨을 나타내는 검증 어노테이션
    private String name; // 회원의 이름

    @NotEmpty // 값이 비어 있으면 안 됨을 나타내는 검증 어노테이션
    private String password; // 회원의 비밀번호

    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>(); // 회원이 등록한 아이템 목록, 'member' 필드에 의해 매핑됨

    public Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    // 기본 생성자
    public Member() {

    }

}