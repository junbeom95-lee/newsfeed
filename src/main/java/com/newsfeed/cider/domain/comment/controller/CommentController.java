package com.newsfeed.cider.domain.comment.controller;

import com.newsfeed.cider.common.model.CommonResponse;
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

    private final CommentService commentService;// 댓글 서비스 의존성 주입


    // 댓글 작성
    @PostMapping("/posts/{postId}/comments") // POST /posts/{postId}/comments
    public ResponseEntity<CommonResponse<CommentCreateResponse>> createComment(
            @SessionAttribute(name = "loginId") Long userId,  // 일관성 위해 required=false
            @PathVariable Long postId,
            @RequestParam(required = false) Long parentId,
            @RequestBody CommentCreateRequest request
    ) {
        CommonResponse<CommentCreateResponse> result = commentService.createComment(userId, postId, parentId, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // 댓글 조회
    @GetMapping("/posts/{postId}/comments") // GET /posts/{postId}/comments
    public ResponseEntity<CommonResponse<List<CommentGetResponse>>> getComments(@PathVariable Long postId) { // 게시글 ID
        CommonResponse<List<CommentGetResponse>> result = commentService.getComments(postId); // 서비스 호출
        return ResponseEntity.status(result.getStatus()).body(result); // 조회 결과 반환
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}") // PUT /comments/{commentId}
    public ResponseEntity<CommonResponse<CommentUpdateResponse>> updateComment(
            @SessionAttribute(name = "loginId") Long userId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request  // CommentRequestDto → CommentUpdateRequest
    ) {
        CommonResponse<CommentUpdateResponse> result = commentService.updateComment(userId, commentId, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}") // DELETE /comments/{commentId}
    public ResponseEntity<CommonResponse<Void>> deleteComment(
            @SessionAttribute(name = "loginId") Long userId,
            @PathVariable Long commentId
    ) {
        CommonResponse<Void> result = commentService.deleteComment(commentId, userId);

        return ResponseEntity.ok(result);
    }
}