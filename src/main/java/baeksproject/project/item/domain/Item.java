package baeksproject.project.item.domain;

import baeksproject.project.login.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private Integer price; //Integer은 int와 다르게 null값이 들어갈수있음.

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public Item() {

    }

}
