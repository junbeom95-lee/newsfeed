package com.newsfeed.cider.domain.follow.model.response;

import com.newsfeed.cider.common.entity.Follow;
import com.newsfeed.cider.common.enums.FollowStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowResponse {
// - Properties
    private final Long followId;
    private final Long followerId;
    private final Long followeeId;
    private final FollowStatus followStatus;

// - Methods
    // - From Entity
    public static FollowResponse from(Follow follow) {
        return new FollowResponse(
                follow.getFollowId(),
                follow.getFollower().getProfileId(),
                follow.getFollowee().getProfileId(),
                follow.getStatus()
        );
    }
}
