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
public class mypageServiceV implements MypageService {

    private final MemberRespository memberRespository; //멤버 레포지토리 의존성 주입
    private final ItemRepository itemRepository; // 아이템 레포지토리 의존성 주입

    @Override
    public List<Item> findMyItem(Long memberId) {
        // 회원 ID를 사용하여 회원 정보를 조회
        Member member = memberRespository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        // 해당 회원 ID로 등록된 아이템을 조회
        return itemRepository.findByMemberId(memberId);
    }
}