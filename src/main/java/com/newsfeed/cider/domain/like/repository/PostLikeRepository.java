package com.newsfeed.cider.domain.like.repository;

import com.newsfeed.cider.common.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByProfile_ProfileIdAndPost_PostId(Long profileId, Long postId);

    long countByPost_PostId(Long postId);

    PostLike findByProfile_ProfileIdAndPost_PostId(Long profileId, Long postId);
}

