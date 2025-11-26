package com.newsfeed.cider.domain.follow.model.response;

import com.newsfeed.cider.common.entity.Follow;
import com.newsfeed.cider.domain.profile.model.response.SummaryProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowRequestResponse {
    private Long followId;
    private SummaryProfileResponse requester;

    public static FollowRequestResponse from(Follow follow) {
        return new FollowRequestResponse(
                follow.getFollowId(),
                SummaryProfileResponse.from(follow.getFollower())
        );
    }
}
