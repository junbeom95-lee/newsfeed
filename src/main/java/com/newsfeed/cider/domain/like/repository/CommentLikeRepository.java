package com.newsfeed.cider.domain.like.repository;

import com.newsfeed.cider.common.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByProfile_ProfileIdAndComment_CommentId(Long profileId, Long commentId);

    long countByComment_CommentId(Long commentId);

    CommentLike findByProfile_ProfileIdAndComment_CommentId(Long profileId, Long commentId);
}
