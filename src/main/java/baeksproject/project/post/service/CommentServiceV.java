package baeksproject.project.post.service;

import baeksproject.project.post.domain.Comment;
import baeksproject.project.post.domain.Post;
import baeksproject.project.post.repository.CommentRepository;
import baeksproject.project.post.repository.CommentUpdateDto;
import baeksproject.project.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceV implements CommentService{

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceV(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public void saveComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);
        commentRepository.save(comment);
    }

//    public void editComment(Long commentId, CommentUpdateDto commentUpdateDto) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//        if (commentUpdateDto.getContent() != null) {
//            comment.setContent(commentUpdateDto.getContent());
//        }
//        commentRepository.save(comment);
//    }


    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
