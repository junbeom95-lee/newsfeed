package com.newsfeed.cider.domain.community.model.response;

import com.newsfeed.cider.domain.community.model.dto.CommunityDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommunityResponse {
    private Long communityId;
    private String communityName;
    private String info;
    private LocalDateTime createdAt;

    public static CreateCommunityResponse from(CommunityDto dto) {
        return new CreateCommunityResponse(
                dto.getCommunityId(),
                dto.getCommunityName(),
                dto.getInfo(),
                dto.getCreatedAt()
        );
    }
}
