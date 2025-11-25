package com.newsfeed.cider.domain.post.repository;

import com.newsfeed.cider.common.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // PostId가 일치하는 단일 Post 찾기
    Optional<Post> findByPostId(Long postId);

    // 그룹의 게시글 페이지 찾기
    Page<Post> findAllByCommunity_CommunityId(Long communityId, Pageable pageable);
}
