package baeksproject.project.mypage.service;

import baeksproject.project.item.domain.Item;

import java.util.List;

public interface MypageService {
    public List<Item> findMyItem(Long memberId);
}
