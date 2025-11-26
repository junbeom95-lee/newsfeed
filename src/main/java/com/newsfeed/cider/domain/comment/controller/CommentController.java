package com.newsfeed.cider.domain.comment.controller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.common.model.SessionUser;
import com.newsfeed.cider.domain.comment.model.request.CommentCreateRequest;
import com.newsfeed.cider.domain.comment.model.request.CommentUpdateRequest;
import com.newsfeed.cider.domain.comment.model.response.CommentCreateResponse;
import com.newsfeed.cider.domain.comment.model.response.CommentUpdateResponseDto;
import com.newsfeed.cider.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
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
    public ResponseEntity<CommonResponse<List<CommentUpdateResponseDto>>> getComments(@PathVariable Long postId) {
        CommonResponse<List<CommentUpdateResponseDto>> result = commentService.getComments(postId);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommonResponse<CommentUpdateResponseDto>> updateComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request  // CommentRequestDto → CommentUpdateRequest
    ) {
        CommonResponse<CommentUpdateResponseDto> result = commentService.updateComment(sessionUser, commentId, request);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId, sessionUser);
        return ResponseEntity.ok("댓글삭제");  // Postman body에 "댓글삭제" 출력, status 200 OK
    }
}