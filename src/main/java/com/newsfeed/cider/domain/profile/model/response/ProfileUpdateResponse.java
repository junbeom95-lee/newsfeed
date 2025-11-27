package com.newsfeed.cider.domain.profile.model.response;


import com.newsfeed.cider.common.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateResponse {
    private Long id;
    private String profileName;
    private String email;
    private LocalDateTime createdAt;
    private Boolean isPrivate;

    public static ProfileUpdateResponse from(Profile profile) {
        return new ProfileUpdateResponse(
                profile.getProfileId(),
                profile.getName(),
                profile.getEmail(),
                profile.getCreatedAt(),
                profile.getIsPrivate()
        );
    }
}
