package com.newsfeed.cider.domain.profile.model.request;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileDeleteRequest {
    @NotBlank
    private String password;


}
