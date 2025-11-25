package com.newsfeed.cider.domain.comment.repository;

import com.newsfeed.cider.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 최상위 댓글만 조회 (parent가 null인 것)
    List<Comment> findByPostIdAndParentIsNullOrderByCreatedAtAsc(Long postId);}