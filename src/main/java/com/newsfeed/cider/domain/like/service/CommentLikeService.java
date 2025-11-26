package com.newsfeed.cider.domain.like.service;

import com.newsfeed.cider.common.entity.*;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.domain.comment.repository.CommentRepository;
import com.newsfeed.cider.domain.like.model.response.CommentLikeResponse;
import com.newsfeed.cider.domain.like.repository.CommentLikeRepository;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.newsfeed.cider.common.enums.ExceptionCode.*;
import static com.newsfeed.cider.common.enums.ExceptionCode.NOT_FOUND_PROFILE;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final ProfileRepository profileRepository;
    private final CommentRepository commentRepository;

    // 댓글에 like
    @Transactional
    public CommentLikeResponse likeComment(Long loginId, Long commentId) {
        Comment comment = getCommentById(commentId);
        Profile profile = getProfile(loginId);
        boolean isLike = commentLikeRepository.existsByProfile_ProfileIdAndComment_CommentId(loginId, commentId);
        if (isLike) {
            throw new CustomException(ALREADY_LIKED);
        }
        CommentLike commentLike = new CommentLike(
                profile,
                comment
        );
        commentLikeRepository.save(commentLike);
        long likesCount = commentLikeRepository.countByComment_CommentId(commentId);
        return new CommentLikeResponse(commentId, true, likesCount);
    }

    // 댓글에 like 취소
    @Transactional
    public CommentLikeResponse unlikeComment(Long loginId, Long commentId) {
        getCommentById(commentId); // Comment가 존재하는지 검증용
        boolean isLike = commentLikeRepository.existsByProfile_ProfileIdAndComment_CommentId(loginId, commentId);
        if (!isLike) {
            throw new CustomException(NOT_LIKED);
        }
        CommentLike commentLike = commentLikeRepository.findByProfile_ProfileIdAndComment_CommentId(loginId, commentId);
        commentLikeRepository.delete(commentLike);
        long likesCount = commentLikeRepository.countByComment_CommentId(commentId);
        return new CommentLikeResponse(commentId, false, likesCount);
    }

    // commentId가 일치하는 comment 가져오기
    // commentId가 일치하는 comment가 없으면 예외 처리
    private Comment getCommentById(Long postId) {
        Comment comment = commentRepository.findById(postId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );
        return comment;
    }

    // profileId 일치하는 Profile 가져오기
    // profileId 일치하는 Profile 없으면 예외 처리
    private Profile getProfile(Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new CustomException(NOT_FOUND_PROFILE)
        );
        return profile;
    }
}
