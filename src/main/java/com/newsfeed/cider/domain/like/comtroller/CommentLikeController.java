package com.newsfeed.cider.domain.like.comtroller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.like.model.response.PostLikeResponse;
import com.newsfeed.cider.domain.like.service.CommentLikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{commentId}/unlikes")
    public ResponseEntity<CommonResponse<PostLikeResponse>> likePost(
            @PathVariable Long commentId,
            HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("loginId");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, commentLikeService.likeComment(loginId, commentId)));
    }

    @DeleteMapping("/{commentId}/likes")
    public ResponseEntity<CommonResponse<PostLikeResponse>> unlikePost(
            @PathVariable Long commentId,
            HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("loginId");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, commentLikeService.unlikeComment(loginId, commentId)));
    }
}
