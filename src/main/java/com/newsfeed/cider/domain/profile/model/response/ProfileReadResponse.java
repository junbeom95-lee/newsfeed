package com.newsfeed.cider.domain.profile.model.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.newsfeed.cider.common.entity.Profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileReadResponse {
    private Long profileId;
    private String profileName;
    private String email;
    private LocalDateTime createdAt;
    private Boolean isPrivate;

    public void setEmail(String email) {
        this.email = email;
    }

    public static ProfileReadResponse from(Profile profile) {
        return new ProfileReadResponse(
                profile.getProfileId(),
                profile.getName(),
                profile.getEmail(),
                profile.getCreatedAt(),
                profile.getIsPrivate()

        );
    }

}
