package com.newsfeed.cider.common.controller;

import com.newsfeed.cider.dto.CommentRequestDto;
import com.newsfeed.cider.dto.CommentResponseDto;
import com.newsfeed.cider.entity.User;
import com.newsfeed.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성 (대댓글 포함)
    @PostMapping("/posts/{postId}/comments")
    public CommentResponseDto createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto dto,
            @AuthenticationPrincipal (User user) {  // 기존 프로젝트는 대부분 이렇게 인증
        return commentService.createComment(postId, dto, user);
    }

    // 해당 게시물의 모든 댓글 (대댓글 트리 포함)
    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public CommentResponseDto updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto dto,
            @com.newsfeed.cider.common.controller.AuthenticationPrincipal User user) {
        return commentService.updateComment(commentId, dto, user);
    }

    // 댓글 삭제 (대댓글도 cascade로 자동 삭제)
    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal User user) {
        commentService.deleteComment(commentId, user);
        return "삭제 완료";
    }
}