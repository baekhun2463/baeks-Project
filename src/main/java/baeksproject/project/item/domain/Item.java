package baeksproject.project.item.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {

    private Long id;

    private String itemName;

    private Integer price; //Integer은 int와 다르게 null값이 들어갈수있음.

    private Integer quantity;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public Item() {

    }

}
