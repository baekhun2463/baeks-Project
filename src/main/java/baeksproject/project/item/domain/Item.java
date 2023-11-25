package baeksproject.project.item.domain;

import baeksproject.project.login.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 10)
    private String itemName;

    private Integer price; //Integer은 int와 다르게 null값이 들어갈수있음.

    private Integer quantity;

    private String imagePath;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Item(String itemName, Integer price, Integer quantity, String imagePath) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    public Item() {

    }

}
