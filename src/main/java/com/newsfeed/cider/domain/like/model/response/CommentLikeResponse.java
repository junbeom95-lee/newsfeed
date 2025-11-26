package com.newsfeed.cider.domain.like.model.response;

import lombok.Getter;

@Getter
public class CommentLikeResponse {

    private final Long commentId;
    private final boolean liked;
    private final long likesCount;

    public CommentLikeResponse(Long commentId, boolean liked, long likesCount) {
        this.commentId = commentId;
        this.liked = liked;
        this.likesCount = likesCount;
    }
}
