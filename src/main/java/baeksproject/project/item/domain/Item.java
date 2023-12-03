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
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "item_name", length = 10)
    private String itemName;
    @NotNull
    @Range(min = 100)
    private Integer price; //Integer은 int와 다르게 null값이 들어갈수있음.
    @NotNull
    @Max(9999)
    private Integer quantity;

    private String imagePath;

    private String mimeType;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Item(String itemName, Integer price, Integer quantity, String imagePath, String mimeType) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
        this.mimeType = mimeType;
    }

    public Item() {

    }

}
