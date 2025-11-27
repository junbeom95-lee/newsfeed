package com.newsfeed.cider.domain.post.repository;

import com.newsfeed.cider.common.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    // PostId가 일치하는 단일 Post 찾기
    Optional<Post> findByPostId(Long postId);

    Page<Post> findAllByProfile_ProfileId(Long profileId, Pageable pageable);

    // 그룹의 게시글 그룹 이름으로 찾기
    Page<Post> findAllByCommunity_CommunityName(String communityName, Pageable pageable);

    long countByCommunity_CommunityId(Long communityCommunityId);

    // 입력한 profileId가 좋아요를 누른 post 페이징 조회
    @Query("select pl.post from PostLike pl where pl.profile.profileId = :profileId")
    Page<Post> findAllLikedPostsByProfileId(@Param("profileId") Long profileId, Pageable pageable);

    @Query("""
            select p
            from Post p
            where p.profile.profileId in (
                select f.followee.profileId
                from Follow f
                where f.follower.profileId = :profileId
                    )
            """)
    Page<Post> findAllFollowedPostsByProfileId(@Param("profileId") Long profileId, Pageable pageable);

    @Modifying
    @Query("update Post p " + "set p.deletedAt = current_timestamp " + "where p.profile.profileId = :profileId")
    void deleteByProfileId(@Param("profileId") Long profileId);
}
