package com.newsfeed.cider.domain.profile.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileUpdateRequest {

    @NotBlank
    @Size(max = 4)
    private String profilename;
    @Email
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;
}
