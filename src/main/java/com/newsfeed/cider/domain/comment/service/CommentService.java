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

    private final CommentRepository commentRepository; // 댓글 관련 DB 접근
    private final PostRepository postRepository; // 게시글 관련 DB 접근
    private final ProfileRepository profileRepository; // 프로필 관련 DB 접근

    // 댓글 등록
    public CommonResponse<CommentCreateResponse> createComment(SessionUser sessionUser, Long postId, Long parentId, CommentCreateRequest request) {

        Post post = postRepository.findByPostId(postId) // 게시글 조회
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_POST)); // 없으면 예외

        Profile profile = profileRepository.findById(sessionUser.getUserId()) // 작성자 프로필 조회
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE)); // 없으면 예외

        Comment comment; // 댓글 객체 생성 준비

        if (parentId == null) { // 부모 댓글이 없는 경우(일반 댓글)

            comment = new Comment(post, profile, null, request.getContent()); // 일반 댓글 생성

        } else { // 대댓글인 경우

            boolean parentCommentExistence = commentRepository.existsById(parentId); // 부모 댓글 존재 여부 확인

            if (parentCommentExistence) comment = new Comment(post, profile, parentId, request.getContent()); // 부모 댓글 존재 시 대댓글 생성
            else comment = new Comment(post, profile, null, request.getContent()); // 존재하지 않으면 일반 댓글로 처리
        }

        Comment savedComment = commentRepository.save(comment); // 댓글 저장

        CommentCreateResponse dto = CommentCreateResponse.from(savedComment); // DTO 변환

        return new CommonResponse<>(HttpStatus.CREATED, dto); // 생성 완료 응답
    }

    // 특정 게시글 댓글 리스트 조회 (공개 조회)
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public CommonResponse<List<CommentGetResponse>> getComments(Long postId) {

        Post post = postRepository.findByPostId(postId) // 게시글 조회
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_POST)); // 없으면 예외

        List<Comment> allComments = commentRepository.findByPost_PostId(post.getPostId()); // 해당 게시글의 전체 댓글 조회

        List<CommentGetResponse> dtos = allComments.stream()
                .map(CommentGetResponse::from) // 엔티티 → DTO 변환
                .collect(Collectors.toList()); // 리스트로 수집

        return new CommonResponse<>(HttpStatus.OK, dtos); // 성공 응답
    }

    // 댓글 수정 (작성자 검증 포함)
    @Transactional
    public CommonResponse<CommentUpdateResponse> updateComment(SessionUser sessionUser, Long commentId, CommentUpdateRequest request) {

        Comment comment = getCommentByIdAndSessionUser(commentId, sessionUser); // 권한 확인 및 댓글 조회

        comment.updateContent(request.getContent()); // 댓글 내용 수정

        CommentUpdateResponse dto = CommentUpdateResponse.from(comment); // 수정된 엔티티 그대로 DTO 변환

        return new CommonResponse<>(HttpStatus.OK, dto); // 성공 응답
    }

    // 댓글 삭제 (작성자 검증 포함)
    @Transactional
    public CommonResponse<Void> deleteComment(Long commentId, SessionUser sessionUser) {

        Comment comment = getCommentByIdAndSessionUser(commentId, sessionUser); // 작성자 검증 후 댓글 조회


        commentRepository.delete(comment); // 댓글 삭제

        return new CommonResponse<>(HttpStatus.NO_CONTENT, null); // 응답 본문 없음
    }

    // 작성자 검증 (조회/수정/삭제에서 공통 사용)
    private Comment getCommentByIdAndSessionUser(Long commentId, SessionUser sessionUser) {

        Comment comment = commentRepository.findById(commentId) // 댓글 조회
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_COMMENT)); // 없으면 예외

        if (!comment.getProfile().getProfileId().equals(sessionUser.getUserId())) { // 작성자 본인인지 검증
            throw new CustomException(ExceptionCode.ACCESS_DENIED); // 권한 없음
        }
        return comment; // 검증 통과 후 반환
    }
}