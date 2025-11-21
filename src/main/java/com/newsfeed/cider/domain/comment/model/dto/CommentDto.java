package com.newsfeed.cider.domain.comment.model.dto;

import com.newsfeed.cider.common.entity.Comment;
import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.common.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long commentId;
    private Post post;
    private Profile profile;
    private Long parentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static CommentDto from(Comment comment) {
        return new CommentDto(
                comment.getCommentId(),
                comment.getPost(),
                comment.getProfile(),
                comment.getParentId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                comment.getDeletedAt()
        );
    }
}
