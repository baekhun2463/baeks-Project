package baeksproject.project.item.repository;

import baeksproject.project.item.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    public Item saveItemWithMember(Long memberId, Item item);

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond cond);
}