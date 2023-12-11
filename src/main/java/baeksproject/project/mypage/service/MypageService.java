package baeksproject.project.mypage.service;

import baeksproject.project.item.domain.Item;
import baeksproject.project.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MypageService {
    public Page<Item> findMyItems(Long memberId, Pageable pageable);
}
