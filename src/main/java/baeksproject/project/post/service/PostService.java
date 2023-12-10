package baeksproject.project.post.service;

import baeksproject.project.post.domain.Post;
import baeksproject.project.post.repository.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    public void save(Post post, Long memberId );

    public void update(Long id, PostUpdateDto updatedPost);

    public Post findById(Long id);

    public List<Post> findAll();
    public void deletePost(Long postId);

    public Page<Post> searchPosts(String keyword, Pageable pageable);

    public Page<Post> findPosts(Pageable pageable);
}
