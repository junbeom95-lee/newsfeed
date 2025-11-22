package com.newsfeed.cider.domain.community.model.request;

import lombok.Getter;

@Getter
public class CreateCommunityRequest {

    private final String communityName;
    private final String info;

    public CreateCommunityRequest(String communityName, String info) {
        this.communityName = communityName;
        this.info = info;
    }
}
