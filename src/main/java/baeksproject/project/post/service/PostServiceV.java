package baeksproject.project.post.service;

import baeksproject.project.post.domain.Post;
import baeksproject.project.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceV implements PostService{

        private final PostRepository postRepository;

        public List<Post> findAll() {
            return postRepository.findAll();
        }

        public Post findById(Long id) {
            Optional<Post> post = postRepository.findById(id);
            if (post.isPresent()) {
                return post.get();
            } else {
                throw new RuntimeException("Post not found for id: " + id);
            }
        }

        public void save(Post post) {
            postRepository.save(post);
        }

        public void update(Long id, Post updatedPost) {
            Post post = findById(id);
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            postRepository.save(post);
        }
}
