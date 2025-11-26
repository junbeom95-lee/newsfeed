package com.newsfeed.cider.domain.post.controller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.post.model.condition.PostSearchCond;
import com.newsfeed.cider.domain.post.model.request.PostCreateRequest;
import com.newsfeed.cider.domain.post.model.request.PostUpdateRequest;
import com.newsfeed.cider.domain.post.model.response.PostCreateResponse;
import com.newsfeed.cider.domain.post.model.response.PostGetResponse;
import com.newsfeed.cider.domain.post.model.response.PostUpdateResponse;
import com.newsfeed.cider.domain.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // Post 생성
    @PostMapping
    public ResponseEntity<CommonResponse<PostCreateResponse>> createPost(
            @Valid @RequestBody PostCreateRequest request, HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("loginId");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(HttpStatus.CREATED, postService.savePost(request, loginId)));
    }

    // 전체 Post 조회
    @GetMapping
    public ResponseEntity<CommonResponse<PagedModel<PostGetResponse>>> getPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "modifiedAt") String sortBy
    ) {
        PagedModel result = new PagedModel<>(postService.getAllPost(page, size, sortBy));
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, result));
    }

    // 특정 조건의 Post만 조회
    @GetMapping("/search")
    public ResponseEntity<CommonResponse<PagedModel<PostGetResponse>>> getSearchedPost(
            @ModelAttribute PostSearchCond condition,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "modifiedAt") String sortBy
    ) {
        PagedModel result = new PagedModel<>(postService.getSearchedPost(condition, page, size, sortBy));
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, result));
    }

    // loginId가 작성한 Post 조회 (My Post 조회)
    @GetMapping("/me")
    public ResponseEntity<CommonResponse<PagedModel<PostGetResponse>>> getPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "modifiedAt") String sortBy,
            HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("loginId");
        PagedModel result = new PagedModel<>(postService.getAllPostById(loginId, page, size, sortBy));
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, result));
    }

    // 단건 Post 조회
    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponse<PostGetResponse>> getPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, postService.getOnePost(postId)));
    }

    // 특정 그룹에 대한 게시글 페이징 조회
    @GetMapping("/{communityName}")
    public ResponseEntity<CommonResponse<PagedModel<PostGetResponse>>> getPost(
            @PathVariable String communityName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, postService.getPostCommunity(communityName, page, size)));
    }

    // Post 수정
    @PutMapping("/{postId}")
    public ResponseEntity<CommonResponse<PostUpdateResponse>> updatePost(
            @PathVariable Long postId, @Valid @RequestBody PostUpdateRequest request, HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("loginId");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, postService.updateService(request, loginId, postId)));
    }

    // Post 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponse> deletePost(@PathVariable Long postId, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        postService.deletePost(loginId, postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
