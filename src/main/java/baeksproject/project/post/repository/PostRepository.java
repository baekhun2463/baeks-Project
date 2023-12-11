package baeksproject.project.post.repository;

import baeksproject.project.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
// PostRepository.java
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword, Pageable pageable);
    Page<Post> findByMemberId(Long memberId, Pageable pageable);

}

