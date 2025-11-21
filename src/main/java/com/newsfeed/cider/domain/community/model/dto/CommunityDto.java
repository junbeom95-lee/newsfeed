package com.newsfeed.cider.domain.community.model.dto;

import com.newsfeed.cider.common.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDto {

    private Long communityId;
    private String communityName;
    private String info;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static CommunityDto from(Community community) {
        return new CommunityDto(
                community.getCommunityId(),
                community.getCommunityName(),
                community.getInfo(),
                community.getCreatedAt(),
                community.getModifiedAt(),
                community.getDeletedAt()
        );
    }
}
