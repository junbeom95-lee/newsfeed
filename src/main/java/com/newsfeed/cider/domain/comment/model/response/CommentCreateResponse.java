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
public class CommentCreateResponse {
    private Long CommentId;
    private String content;
    private String postTitle;
    private String postContent;
    private String profileName;
    private Long likeCount;
    private Long parentId;
    private LocalDateTime createdAt;

    public static CommentCreateResponse from(Comment comment) {

        return new CommentCreateResponse(
                comment.getCommentId(),
                comment.getPost().getTitle(),
                comment.getPost().getContent(),
                comment.getContent(),
                comment.getProfile().getName(),
                comment.getLikeCount(),
                comment.getParentId(),
                comment.getCreatedAt());
    }
}
