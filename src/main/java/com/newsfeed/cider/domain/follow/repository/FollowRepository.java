package com.newsfeed.cider.domain.follow.repository;

import com.newsfeed.cider.common.entity.Follow;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.FollowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // - Find All By Follower
    List<Follow> findAllByFollowerAndStatus(Profile follower, FollowStatus status);
    // - Find All By Followee
    List<Follow> findAllByFolloweeAndStatus(Profile followee, FollowStatus status);
    // - Find By Follower And Followee
    Optional<Follow> findByFollowerAndFolloweeAndStatus(Profile follower, Profile followee, FollowStatus status);
    // - ExistsByFollowerAndFollowee
    boolean existsByFollowerAndFolloweeAndStatus(Profile follower, Profile followee, FollowStatus status);
}