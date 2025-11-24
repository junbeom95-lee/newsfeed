package com.newsfeed.service;

import com.newsfeed.dto.CommentRequestDto;
import com.newsfeed.dto.CommentResponseDto;
import com.newsfeed.entity.Comment;
import com.newsfeed.entity.Post;
import com.newsfeed.entity.User;
import com.newsfeed.repository.CommentRepository;
import com.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto dto, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        Comment parent = null;
        if (dto.getParentId() != null) {
            parent = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = new Comment(dto.getContent(), user, post, parent);
        comment = commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getComments(Long postId) {
        return commentRepository.findByPostIdAndParentIsNullOrderByCreatedAtAsc(postId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto dto, User user) {
        Comment comment = getCommentByIdAndUser(commentId, user);
        comment.update(dto.getContent());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = getCommentByIdAndUser(commentId, user);
        commentRepository.delete(comment);
    }

    private Comment getCommentByIdAndUser(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정/삭제할 수 있습니다.");
        }
        return comment;
    }
}