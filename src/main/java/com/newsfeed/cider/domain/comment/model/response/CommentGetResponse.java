package com.newsfeed.cider.domain.comment.model.response;

import com.newsfeed.cider.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentGetResponse {
    private Long commentId;
    private String content;
    private String postTitle;
    private String postContent;
    private String profileName;
    private Long parentId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentGetResponse from(Comment comment) {
        return new CommentGetResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getPost().getTitle(),
                comment.getPost().getContent(),
                comment.getProfile().getName(),
                comment.getParentId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}