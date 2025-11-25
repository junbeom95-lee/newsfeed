package com.newsfeed.cider.domain.community.model.response;

import com.newsfeed.cider.common.entity.Community;
import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.domain.post.model.response.PostGetResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityGetOneResponse {

    private Long communityId;
    private String communityName;
    private String info;
    private PagedModel<PostGetResponse> postPage;
    private LocalDateTime createdAt;

    public static CommunityGetOneResponse from(Community community, Page<Post> postPage) {

        Page<PostGetResponse> postGetResponsePage =  postPage.map(PostGetResponse::from);

        PagedModel<PostGetResponse> postPagedModel = new PagedModel<>(postGetResponsePage);

        return new CommunityGetOneResponse(
                community.getCommunityId(),
                community.getCommunityName(),
                community.getInfo(),
                postPagedModel,
                community.getCreatedAt()
        );
    }
}
