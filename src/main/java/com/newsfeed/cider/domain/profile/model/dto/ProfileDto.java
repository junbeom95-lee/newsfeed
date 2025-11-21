package com.newsfeed.cider.domain.profile.model.dto;

import com.newsfeed.cider.common.entity.Group;
import com.newsfeed.cider.common.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private Long profileId;
    private String name;
    private String email;
    private String password;
    private List<Group> groupList;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ProfileDto from(Profile profile) {
        return new ProfileDto(
                profile.getProfileId(),
                profile.getName(),
                profile.getEmail(),
                profile.getPassword(),
                profile.getGroupList(),
                profile.getDeletedAt(),
                profile.getCreatedAt(),
                profile.getModifiedAt()
        );
    }
}
