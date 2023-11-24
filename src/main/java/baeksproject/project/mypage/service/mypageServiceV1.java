package baeksproject.project.mypage.service;

import baeksproject.project.item.domain.Item;
import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class mypageServiceV1 implements MypageService {

    private final MemberRespository memberRespository;
    private final ItemRepository itemRepository;


    @Override
    public List<Item> findMyItem(Long memberId) {
        Member member = memberRespository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return itemRepository.findByMemberId(memberId);
    }
}
