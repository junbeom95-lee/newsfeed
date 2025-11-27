package com.newsfeed.cider.domain.comment.repository;

import com.newsfeed.cider.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByPost_PostId(Long postId);


}