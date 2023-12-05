package baeksproject.project.item.domain;

import baeksproject.project.login.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter @Setter
@Entity // JPA 엔티티임을 나타내며, 이 클래스의 인스턴스는 데이터베이스의 행에 매핑됩니다.
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 아이템의 고유 ID, 데이터베이스에서 자동 생성됩니다.

    @NotBlank // 값이 비어 있으면 안 됨을 나타내는 검증 어노테이션
    @Column(name = "item_name", length = 10) // 데이터베이스의 컬럼과 매핑, 컬럼 이름과 길이 지정
    private String itemName;

    @NotNull // 값이 null이면 안 됨을 나타내는 검증 어노테이션
    @Range(min = 100) // 값의 범위를 지정하는 검증 어노테이션
    private Integer price;

    @NotNull // 값이 null이면 안 됨을 나타내는 검증 어노테이션
    @Max(9999) // 최대값을 지정하는 검증 어노테이션
    private Integer quantity;

    private String imagePath; // 아이템 이미지의 경로
    private String mimeType; // 아이템 이미지의 MIME 타입

    @ManyToOne // 다대일 관계를 나타내는 어노테이션
    @JoinColumn(name = "member_id") // 외래키를 지정하는 어노테이션
    private Member member;

    public Item(String itemName, Integer price, Integer quantity, String imagePath, String mimeType) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
        this.mimeType = mimeType;
    }

    // 기본 생성자
    public Item() {

    }
}
