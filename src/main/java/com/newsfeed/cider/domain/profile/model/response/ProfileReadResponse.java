package com.newsfeed.cider.domain.profile.model.response;


import com.newsfeed.cider.domain.profile.model.dto.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileReadResponse {
    private long profileId;
    private String profilename;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static ProfileReadResponse from(ProfileDto dto) {
        return new ProfileReadResponse(
                dto.getProfileId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getCreatedAt(),
                dto.getModifiedAt(),
                dto.getDeletedAt()

        );
    }

}
