package com.newsfeed.cider.domain.comment.controller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.common.model.SessionUser;
import com.newsfeed.cider.domain.comment.model.request.CommentCreateRequest;
import com.newsfeed.cider.domain.comment.model.request.CommentUpdateRequest;
import com.newsfeed.cider.domain.comment.model.response.CommentCreateResponse;
import com.newsfeed.cider.domain.comment.model.response.CommentGetResponse;
import com.newsfeed.cider.domain.comment.model.response.CommentUpdateResponse;
import com.newsfeed.cider.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommonResponse<CommentCreateResponse>> createComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,  // 일관성 위해 required=false
            @PathVariable Long postId,
            @RequestParam(required = false) Long parentId,
            @RequestBody CommentCreateRequest request
    ) {
        CommonResponse<CommentCreateResponse> result = commentService.createComment(sessionUser, postId, parentId, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // 댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<CommonResponse<List<CommentGetResponse>>> getComments(@PathVariable Long postId) {
        CommonResponse<List<CommentGetResponse>> result = commentService.getComments(postId);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommonResponse<CommentUpdateResponse>> updateComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request  // CommentRequestDto → CommentUpdateRequest
    ) {
        CommonResponse<CommentUpdateResponse> result = commentService.updateComment(sessionUser, commentId, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommonResponse<Void>> deleteComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId
    ) {
        CommonResponse<Void> result = commentService.deleteComment(commentId, sessionUser);

        return ResponseEntity.ok(result);
    }
}