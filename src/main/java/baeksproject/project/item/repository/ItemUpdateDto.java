package baeksproject.project.item.repository;

import lombok.Getter;
import lombok.Setter;

/**
 * 상품을 수정할때 사용하는 객체 (Data Transfer Object)
 */
@Getter @Setter
public class ItemUpdateDto {

    private String itemName;

    private Integer price;

    private Integer quantity;

    public ItemUpdateDto() {}

    public ItemUpdateDto(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}