package baeksproject.project.mypage.service;

import baeksproject.project.item.domain.Item;
import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class mypageService {

    private final MemberRespository memberRespository;
    private final ItemRepository itemRepository;

    public Item saveItemWithMember(Long memberId, Item item) {
        Member member = memberRespository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        item.setMember(member);
        return itemRepository.save(item);
    }

}
