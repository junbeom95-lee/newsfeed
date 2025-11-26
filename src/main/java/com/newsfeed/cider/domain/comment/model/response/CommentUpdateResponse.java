package com.newsfeed.cider.domain.comment.model.response;

import com.newsfeed.cider.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateResponse {
    private Long commentId;
    private String content;
    private String postTitle;
    private String postContent;
    private String profileName;
    private LocalDateTime modifiedAt;

    public static CommentUpdateResponse from(Comment comment) {
        return new CommentUpdateResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getPost().getTitle(),
                comment.getPost().getContent(),
                comment.getProfile().getName(),
                comment.getModifiedAt());
    }
}