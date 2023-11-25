package baeksproject.project.item.service;

import baeksproject.project.item.domain.Item;
import baeksproject.project.item.repository.ItemSearchCond;
import baeksproject.project.item.repository.ItemUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    public Item saveItemWithMember(Long memberId, Item item, MultipartFile imageFile);
    void update(Long itemId, ItemUpdateDto updateParam);
    Optional<Item> findById(Long id);
    List<Item> findItems(ItemSearchCond cond);
}
