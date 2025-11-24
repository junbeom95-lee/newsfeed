package com.newsfeed.cider.domain.follow.model.dto;

import com.newsfeed.cider.common.entity.Follow;
import com.newsfeed.cider.common.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {

    private Long followId;
    private Profile following;
    private Profile follower;

    public static FollowDto from(Follow follow) {
        return new FollowDto(
                follow.getFollowId(),
                follow.getFollowee(),
                follow.getFollower()
        );
    }
}
