package baeksproject.project.post.service;

import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.MemberRespository;
import baeksproject.project.post.domain.Post;
import baeksproject.project.post.repository.PostRepository;
import baeksproject.project.post.repository.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceV implements PostService{

    private final MemberRespository memberRespository;
    private final PostRepository postRepository;

    public PostServiceV(MemberRespository memberRespository, PostRepository postRepository) {
        this.memberRespository = memberRespository;
        this.postRepository = postRepository;
    }

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

        public void save(Post post, Long memberId) {
            Member member = memberRespository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found")); // 회원 ID로 회원 조회, 없으면 예외 발생
            post.setMember(member); // 아이템에 회원 정보 설정
            postRepository.save(post);
        }

    public void update(Long postId, PostUpdateDto updateDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(updateDto.getTitle());
        post.setContent(updateDto.getContent());
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public Page<Post> searchPosts(String keyword, Pageable pageable) {
        // 검색 및 페이징 처리 로직 구현
        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
    }

    public Page<Post> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

}
