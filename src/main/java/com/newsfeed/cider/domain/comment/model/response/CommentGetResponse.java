package com.newsfeed.cider.domain.comment.model.response;

import com.newsfeed.cider.common.entity.Comment;
import com.newsfeed.cider.common.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetResponse {
    private Long commentId;
    private String content;
    private String postTitle;
    private String postContent;
    private String profileName;
    private Long likeCount;
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
                comment.getLikeCount(),
                comment.getParentId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}