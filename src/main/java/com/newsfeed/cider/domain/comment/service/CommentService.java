package com.newsfeed.cider.domain.comment.service;

import com.newsfeed.cider.common.entity.Comment;
import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.common.model.SessionUser;
import com.newsfeed.cider.domain.comment.model.request.CommentCreateRequest;
import com.newsfeed.cider.domain.comment.model.request.CommentUpdateRequest;
import com.newsfeed.cider.domain.comment.model.response.CommentCreateResponse;
import com.newsfeed.cider.domain.comment.model.response.CommentUpdateResponseDto;
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
    public CommonResponse<CommentCreateResponse> createComment(SessionUser sessionUser, Long postId, Long parentId, CommentCreateRequest request) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_POST));

        Profile profile = profileRepository.findById(sessionUser.getUserId())
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        Comment comment = new Comment(post, profile, parentId, request.getContent());

        Comment savedComment = commentRepository.save(comment);

        CommentCreateResponse dto = CommentCreateResponse.from(savedComment);

        return new CommonResponse<>(HttpStatus.CREATED, dto);
    }

    // 댓글 조회 (공개 조회이므로 작성자 검증 생략; 전체 댓글 리스트 반환)
    @Transactional(readOnly = true)
    public CommonResponse<List<CommentUpdateResponseDto>> getComments(Long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_POST));

        List<Comment> allComments = commentRepository.findByPost(post);
        List<Comment> topLevelComments = allComments.stream()
                .filter(c -> c.getParentId() == null)
                .collect(Collectors.toList());

        List<CommentUpdateResponseDto> dtos = topLevelComments.stream()
                .map(comment -> CommentUpdateResponseDto.from(comment, getChildrenComments(comment, allComments)))
                .collect(Collectors.toList());

        return new CommonResponse<>(HttpStatus.OK, dtos);
    }

    // 댓글 수정 (작성자 검증 포함)
    @Transactional
    public CommonResponse<CommentUpdateResponseDto> updateComment(SessionUser sessionUser, Long commentId, CommentUpdateRequest request) {
        Comment comment = getCommentByIdAndSessionUser(commentId, sessionUser);

        comment.updateContent(request.getContent());  // 엔티티 update 메서드 호출 가정
        Comment updatedComment = commentRepository.save(comment);

        // 자식 댓글 리스트 조회
        List<Comment> children = commentRepository.findByParentId(commentId);

        CommentUpdateResponseDto dto = CommentUpdateResponseDto.from(updatedComment, children);

        return new CommonResponse<>(HttpStatus.OK, dto);
    }

    // 댓글 삭제 (작성자 검증 포함)
    @Transactional
    public void deleteComment(Long commentId, SessionUser sessionUser) {
        Comment comment = getCommentByIdAndSessionUser(commentId, sessionUser);
        commentRepository.delete(comment);
    }

    // 작성자 검증 (공통 메서드: 조회/수정/삭제에서 재사용)
    private Comment getCommentByIdAndSessionUser(Long commentId, SessionUser sessionUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_COMMENT));

        if (!comment.getProfile().getProfileId().equals(sessionUser.getUserId())) {
            throw new CustomException(ExceptionCode.ACCESS_DENIED);  // 권한 없음: ACCESS_DENIED 사용
        }
        return comment;
    }

    // 자식 댓글 재귀 조회 (트리 빌드 헬퍼)
    private List<Comment> getChildrenComments(Comment parent, List<Comment> allComments) {
        return allComments.stream()
                .filter(c -> parent.getCommentId().equals(c.getParentId()))
                .collect(Collectors.toList());
    }
}