package com.newsfeed.cider.domain.profile_community.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCommunityResponse {

    private Long communityId;
    private String communityName;
    private LocalDateTime joinedAt;
}
