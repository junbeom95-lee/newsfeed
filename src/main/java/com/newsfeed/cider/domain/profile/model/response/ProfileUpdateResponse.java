package com.newsfeed.cider.domain.profile.model.response;

import com.newsfeed.cider.domain.profile.model.dto.ProfileDto;
import com.newsfeed.cider.domain.profile.model.request.ProfileUpdateRequest;
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
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static ProfileUpdateResponse from(ProfileDto dto) {
        return new ProfileUpdateResponse(
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
