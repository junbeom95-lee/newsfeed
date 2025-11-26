package com.newsfeed.cider.domain.like.service;

import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.common.entity.PostLike;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.domain.like.model.response.PostLikeResponse;
import com.newsfeed.cider.domain.like.repository.PostLikeRepository;
import com.newsfeed.cider.domain.post.repository.PostRepository;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.newsfeed.cider.common.enums.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    // 게시글에 like
    @Transactional
    public PostLikeResponse likePost(Long loginId, Long postId) {
        Post post = getPostById(postId);
        Profile profile = getProfile(loginId);
        boolean isLike = postLikeRepository.existsByProfile_ProfileIdAndPost_PostId(loginId, postId);
        if (isLike) {
            throw new CustomException(ALREADY_LIKED);
        }
        PostLike postLike = new PostLike(
                profile,
                post
        );
        postLikeRepository.save(postLike);
        post.increaseLikeCount();
        return new PostLikeResponse(postId, true, post.getLikeCount());
    }

    // 게시글에 like 취소
    @Transactional
    public PostLikeResponse unlikePost(Long loginId, Long postId) {
        Post post = getPostById(postId);
        boolean isLike = postLikeRepository.existsByProfile_ProfileIdAndPost_PostId(loginId, postId);
        if (!isLike) {
            throw new CustomException(NOT_LIKED);
        }
        PostLike postLike = postLikeRepository.findByProfile_ProfileIdAndPost_PostId(loginId, postId);
        postLikeRepository.delete(postLike);
        post.decreaseLikeCount();
        return new PostLikeResponse(postId, false, post.getLikeCount());
    }

    // postId가 일치하는 Post 가져오기
    // postID가 일치하는 Post가 없으면 예외 처리
    private Post getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(NOT_FOUND_POST)
        );
        return post;
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
