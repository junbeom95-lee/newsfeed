package com.newsfeed.cider.domain.post.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String communityName;
}
