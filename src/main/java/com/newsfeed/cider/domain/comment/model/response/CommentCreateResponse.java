package com.newsfeed.cider.domain.comment.model.response;

import com.newsfeed.cider.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateResponse {
    private Long CommentId;
    private String content;
    private String postTitle;
    private String postContent;
    private String profileName;
    private Long parentId;
    private LocalDateTime createdAt;

    public static CommentCreateResponse from(Comment comment) {

        return new CommentCreateResponse(
                comment.getCommentId(),
                comment.getPost().getTitle(),
                comment.getPost().getContent(),
                comment.getContent(),
                comment.getProfile().getName(),
                comment.getParentId(),
                comment.getCreatedAt());
    }
}
