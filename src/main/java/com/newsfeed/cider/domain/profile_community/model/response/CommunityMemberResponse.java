package com.newsfeed.cider.domain.profile_community.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityMemberResponse {
    private Long profileId;
    private String profileName;
    private LocalDateTime joinedAt;
}
