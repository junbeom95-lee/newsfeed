package com.newsfeed.cider.domain.comment.service;

import com.newsfeed.cider.common.entity.Comment;
import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.common.util.AuthManager;
import com.newsfeed.cider.domain.comment.model.request.CommentCreateRequest;
import com.newsfeed.cider.domain.comment.model.request.CommentUpdateRequest;
import com.newsfeed.cider.domain.comment.model.response.CommentCreateResponse;
import com.newsfeed.cider.domain.comment.model.response.CommentGetResponse;
import com.newsfeed.cider.domain.comment.model.response.CommentUpdateResponse;
import com.newsfeed.cider.domain.comment.repository.CommentRepository;
import com.newsfeed.cider.domain.post.repository.PostRepository;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    // 댓글 등록
    public CommonResponse<CommentCreateResponse> createComment(Long userId, Long postId, Long parentId, CommentCreateRequest request) {

        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_POST));

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        Comment comment;

        if (parentId == null) {

            comment = new Comment(post, profile, null, request.getContent());

        } else {

            boolean parentCommentExistence = commentRepository.existsById(parentId);

            if (parentCommentExistence) comment = new Comment(post, profile, parentId, request.getContent());
            else comment = new Comment(post, profile, null, request.getContent());
        }

        Comment savedComment = commentRepository.save(comment);

        CommentCreateResponse dto = CommentCreateResponse.from(savedComment);

        return new CommonResponse<>(HttpStatus.CREATED, dto);
    }

    // 특정 게시글 댓글 리스트 조회 (공개 조회이므로 작성자 검증 생략; 전체 댓글 리스트 반환)
    @Transactional(readOnly = true)
    public CommonResponse<List<CommentGetResponse>> getComments(Long postId) {

        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_POST));

        List<Comment> allComments = commentRepository.findByPost_PostId(post.getPostId());

        List<CommentGetResponse> dtos = allComments.stream()
                .map(CommentGetResponse::from)
                .collect(Collectors.toList());

        return new CommonResponse<>(HttpStatus.OK, dtos);
    }

    // 댓글 수정 (작성자 검증 포함)
    @Transactional
    public CommonResponse<CommentUpdateResponse> updateComment(Long userId, Long commentId, CommentUpdateRequest request) {

        Comment comment = getCommentByIdAndSessionUser(commentId, userId);

        comment.updateContent(request.getContent());  // 엔티티 update 메서드 호출 가정

        Comment updatedComment = commentRepository.save(comment);

        CommentUpdateResponse dto = CommentUpdateResponse.from(updatedComment);

        return new CommonResponse<>(HttpStatus.OK, dto);
    }

    // 댓글 삭제 (작성자 검증 포함)
    @Transactional
    public CommonResponse<Void> deleteComment(Long commentId, Long userId) {

        Comment comment = getCommentByIdAndSessionUser(commentId, userId);

        commentRepository.delete(comment);

        return new CommonResponse<>(HttpStatus.NO_CONTENT, null);
    }

    // 작성자 검증 (공통 메서드: 조회/수정/삭제에서 재사용)
    private Comment getCommentByIdAndSessionUser(Long commentId, Long userId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_COMMENT));

        AuthManager.validateAuthorization(commentId, userId);

        return comment;
    }
}