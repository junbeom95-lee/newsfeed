package com.newsfeed.cider.domain.like.repository;

import com.newsfeed.cider.common.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
