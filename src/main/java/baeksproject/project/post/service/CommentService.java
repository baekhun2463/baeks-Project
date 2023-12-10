package baeksproject.project.post.service;

import baeksproject.project.post.domain.Comment;
import baeksproject.project.post.repository.CommentUpdateDto;

import java.util.List;

public interface CommentService {
    public void saveComment(Long postId, Comment comment);
    public void deleteComment(Long commentId);
   // public void editComment(Long commentId, CommentUpdateDto updateDto);

}
