package baeksproject.project.item.repository;

import lombok.Getter;
import lombok.Setter;

/**
 * 검색 조건으로 사용되는 객체
 */
@Setter @Getter
public class ItemSearchCond {

    private String itemName;

    private Integer maxPrice;

    public ItemSearchCond() {}

    public ItemSearchCond(String itemName, Integer maxPrice) {
        this.itemName = itemName;
        this.maxPrice = maxPrice;
    }
}
