package com.newsfeed.cider.domain.post.repository;

import com.newsfeed.cider.common.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByPostId(Long postId);
}
