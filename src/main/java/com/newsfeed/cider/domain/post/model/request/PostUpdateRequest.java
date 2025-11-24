package com.newsfeed.cider.domain.post.model.request;

import lombok.Getter;

@Getter
public class PostUpdateRequest {

    private String title;

    private String content;
}
