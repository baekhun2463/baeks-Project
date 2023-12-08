package baeksproject.project.post.service;

import baeksproject.project.post.domain.Post;

import java.util.List;

public interface PostService {
    public void save(Post post);

    public void update(Long id, Post updatedPost);

    public Post findById(Long id);

    public List<Post> findAll();
}
