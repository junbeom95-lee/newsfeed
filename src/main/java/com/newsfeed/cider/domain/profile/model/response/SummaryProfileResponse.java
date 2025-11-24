package com.newsfeed.cider.domain.profile.model.response;

import lombok.Getter;

@Getter
public class SummaryProfileResponse {
// - Properties
    private final Long profileId;
    private final String name;

// - Methods
    // - Constructor
    public SummaryProfileResponse(Long profileId, String name) {
        this.profileId = profileId;
        this.name = name;
    }
}
