package com.newsfeed.cider.domain.follow.model.dto;

import com.newsfeed.cider.common.entity.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {

    private Long followId;
    private Follow following;
    private Follow follower;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static FollowDto from(Follow follow) {
        return new FollowDto(
                follow.getFollowId(),
                follow.getFollowing(),
                follow.getFollower(),
                follow.getCreatedAt(),
                follow.getModifiedAt()
        );
    }
}
