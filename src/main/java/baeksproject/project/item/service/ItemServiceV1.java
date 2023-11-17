package baeksproject.project.item.service;

import baeksproject.project.item.domain.Item;
import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.item.repository.ItemSearchCond;
import baeksproject.project.item.repository.ItemUpdateDto;
import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import baeksproject.project.login.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceV1 implements ItemService{

    private final ItemRepository itemRepository;
    private final MemberRespository memberRespository;


    public Item saveItemWithMember(Long memberId, Item item) {
        Member member = memberRespository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        log.info("member={}", member);
        item.setMember(member);
        return itemRepository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        itemRepository.update(itemId, updateParam);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> findItems(ItemSearchCond cond) {
        return itemRepository.findAll(cond);
    }
}
