package com.newsfeed.cider.domain.post.controller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.post.model.request.PostCreateRequest;
import com.newsfeed.cider.domain.post.model.request.PostUpdateRequest;
import com.newsfeed.cider.domain.post.model.response.PostCreateResponse;
import com.newsfeed.cider.domain.post.model.response.PostUpdateResponse;
import com.newsfeed.cider.domain.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CommonResponse<PostCreateResponse>> createPost(
            @Valid @RequestBody PostCreateRequest request, HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("loginId");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(HttpStatus.CREATED, postService.savePost(request, loginId)));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<CommonResponse<PostUpdateResponse>> updatePost(
            @PathVariable Long postId, @Valid @RequestBody PostUpdateRequest request, HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("loginId");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, postService.updateService(request, loginId, postId)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponse> deletePost(@PathVariable Long postId, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        postService.deletePost(loginId, postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
