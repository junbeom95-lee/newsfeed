package com.newsfeed.cider.domain.community.model.request;

import lombok.Getter;

@Getter
public class UpdateCommunityRequest {

    private String communityName;
    private String info;

    public UpdateCommunityRequest(String communityName, String info) {
        this.communityName = communityName;
        this.info = info;
    }
}
