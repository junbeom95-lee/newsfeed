package com.newsfeed.cider.domain.comment.repository;

import com.newsfeed.cider.common.entity.Comment;
import com.newsfeed.cider.common.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByParentId(Long parentId);

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.post p LEFT JOIN FETCH p.profile LEFT JOIN FETCH c.profile WHERE c.parentId = :parentId")
    List<Comment> findByParentIdWithAssociations(@Param("parentId") Long parentId);
}