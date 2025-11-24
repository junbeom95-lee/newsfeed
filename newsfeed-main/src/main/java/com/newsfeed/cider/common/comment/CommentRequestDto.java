package com.newsfeed.cider.common.comment;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;
    private Long parentId;  // null이면 최상위 댓글, 값 있으면 대댓글
}