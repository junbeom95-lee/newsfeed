package com.newsfeed.cider.domain.follow.model.response;

import lombok.Getter;

@Getter
public class FollowResponse {
// - Properties
    private final Long followerId;
    private final Long followeeId;

// - Methods
    // - Constructor
    public FollowResponse(Long followerId, Long followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }
}
