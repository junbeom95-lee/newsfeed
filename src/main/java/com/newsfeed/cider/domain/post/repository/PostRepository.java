package com.newsfeed.cider.domain.post.repository;

import com.newsfeed.cider.common.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
