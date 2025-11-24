package com.newsfeed.cider.domain.profile.model.response;

import com.newsfeed.cider.domain.profile.model.dto.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCreateResponse {
    private Long id;
    private String email;
    private String profilename;
    private String password;

    public static ProfileCreateResponse from(ProfileDto dto) {
        return new ProfileCreateResponse(
                dto.getProfileId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPassword()
        );
    }

}
