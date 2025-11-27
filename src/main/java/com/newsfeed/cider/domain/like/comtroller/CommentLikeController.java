package com.newsfeed.cider.domain.like.comtroller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.like.model.response.CommentLikeResponse;
import com.newsfeed.cider.domain.like.service.CommentLikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{commentId}/likes")
    public ResponseEntity<CommonResponse<CommentLikeResponse>> likePost(
            @PathVariable Long commentId,
            HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("loginId");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, commentLikeService.likeComment(loginId, commentId)));
    }

    @DeleteMapping("/{commentId}/likes")
    public ResponseEntity<CommonResponse<CommentLikeResponse>> unlikePost(
            @PathVariable Long commentId,
            HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("loginId");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, commentLikeService.unlikeComment(loginId, commentId)));
    }
}
