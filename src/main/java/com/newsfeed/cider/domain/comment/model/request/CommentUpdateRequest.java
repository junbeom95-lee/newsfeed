package com.newsfeed.cider.domain.comment.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CommentUpdateRequest {
    private String content;

}