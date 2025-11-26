package com.newsfeed.cider.domain.like.model.response;

import lombok.Getter;

@Getter
public class PostLikeResponse {

    private final Long postId;
    private final boolean liked;
    private final long likesCount;

    public PostLikeResponse(Long postId, boolean liked, long likesCount) {
        this.postId = postId;
        this.liked = liked;
        this.likesCount = likesCount;
    }
}
