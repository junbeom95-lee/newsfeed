package com.newsfeed.cider.domain.post.model.response;

import com.newsfeed.cider.common.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostGetResponse {

    private final Long id;
    private final String name;
    private final String communityName;
    private final String title;
    private final String content;
    private final Long likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostGetResponse(Long id, String name, String communityName, String title, String content, Long likeCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.communityName = communityName;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // Post를 PostGetResponse 만들어주는 정적 팩토리 메서드
    public static PostGetResponse from(Post post) {

        String communityName = null;
        if (post.getCommunity() != null) {
            communityName = post.getCommunity().getCommunityName();
        }

        return new PostGetResponse(
                post.getPostId(),
                post.getProfile().getName(),
                communityName,
                post.getTitle(),
                post.getContent(),
                post.getLikeCount(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}
