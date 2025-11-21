package com.newsfeed.cider.domain.post.model.dto;

import com.newsfeed.cider.common.entity.Comment;
import com.newsfeed.cider.common.entity.Group;
import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.common.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long postId;
    private Profile profile;
    private String title;
    private String content;
    private List<Comment> commentList;
    private Group group;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static PostDto from(Post post) {
        return new PostDto(
                post.getPostId(),
                post.getProfile(),
                post.getTitle(),
                post.getContent(),
                post.getCommentList(),
                post.getGroup(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getDeletedAt()
        );
    }
}
