package com.newsfeed.cider.domain.like.comtroller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.like.model.response.PostLikeResponse;
import com.newsfeed.cider.domain.like.service.PostLikeService;
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
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/likes")
    public ResponseEntity<CommonResponse<PostLikeResponse>> likePost(
            @PathVariable Long postId,
            HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("loginId");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, postLikeService.likePost(loginId, postId)));
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<CommonResponse<PostLikeResponse>> unlikePost(
            @PathVariable Long postId,
            HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("loginId");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, postLikeService.unlikePost(loginId, postId)));
    }
}
