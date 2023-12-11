package baeksproject.project.item.repository;

import baeksproject.project.item.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ItemRepository{

    public Page<Item> findPaginatedItems(ItemSearchCond itemSearch, int page, int size);

    public long countItems();

    public Item saveItemWithMember(Long memberId, Item item);

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond cond);

    public Page<Item> findByMemberId(Long memberId, Pageable pageable);

    void deleteById(Long id);
}