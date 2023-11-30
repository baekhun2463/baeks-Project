package baeksproject.project.item.service;

import baeksproject.project.item.domain.Item;
import baeksproject.project.item.repository.ItemSearchCond;
import baeksproject.project.item.repository.ItemUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    public Page<Item> findItems(ItemSearchCond itemSearch, int page, int size);
    public Item saveItemWithMember(Long memberId, Item item, MultipartFile imageFile);
    void update(Long itemId, ItemUpdateDto updateParam, MultipartFile imageFile, Long currentMemberId);
    Optional<Item> findById(Long id);
    List<Item> findItems(ItemSearchCond cond);
    public void deleteItem(Long itemId, Long currentMemberId);
}
