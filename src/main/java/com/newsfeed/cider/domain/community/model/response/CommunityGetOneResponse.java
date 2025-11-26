package com.newsfeed.cider.domain.community.model.response;

import com.newsfeed.cider.common.entity.Community;
import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.domain.post.model.response.PostGetResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityGetOneResponse {

    private Long communityId;
    private String communityName;
    private String info;
    private Long countPost;
    private LocalDateTime createdAt;

    public static CommunityGetOneResponse from(Community community, long countPost) {
        return new CommunityGetOneResponse(
                community.getCommunityId(),
                community.getCommunityName(),
                community.getInfo(),
                countPost,
                community.getCreatedAt()
        );
    }
}
