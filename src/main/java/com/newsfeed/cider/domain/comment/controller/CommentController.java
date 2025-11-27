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

    private final CommentService commentService;// 댓글 서비스 의존성 주입


    // 댓글 작성
    @PostMapping("/posts/{postId}/comments") // POST /posts/{postId}/comments
    public ResponseEntity<CommonResponse<CommentCreateResponse>> createComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,  // 세션 로그인 유저
            @PathVariable Long postId, // 게시글 ID
            @RequestParam(required = false) Long parentId, // 부모 댓글 ID (nullable → 대댓글)
            @RequestBody CommentCreateRequest request // 생성 요청 본문
    ) {
        CommonResponse<CommentCreateResponse> result = commentService.createComment(sessionUser, postId, parentId, request); // 서비스 호출
        return ResponseEntity.status(result.getStatus()).body(result); // 상태 코드 + 응답 반환
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
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, // 세션 사용자
            @PathVariable Long commentId, // 수정 대상 댓글 ID
            @RequestBody CommentUpdateRequest request // 수정 요청 본문
    ) {
        CommonResponse<CommentUpdateResponse> result = commentService.updateComment(sessionUser, commentId, request); // 서비스 호출
        return ResponseEntity.status(result.getStatus()).body(result); // 수정 결과 반환
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}") // DELETE /comments/{commentId}
    public ResponseEntity<CommonResponse<Void>> deleteComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, // 세션 사용자
            @PathVariable Long commentId // 삭제할 댓글 ID
    ) {
        CommonResponse<Void> result = commentService.deleteComment(commentId, sessionUser); // 서비스 호출
        return ResponseEntity.ok(result); // 삭제 성공 응답
    }
}