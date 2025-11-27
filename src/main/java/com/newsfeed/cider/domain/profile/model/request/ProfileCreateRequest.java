package com.newsfeed.cider.domain.profile.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileCreateRequest {

    @Email
    private String email;

    @NotBlank
    @Size(max = 4)
    private String profileName;

    @NotBlank
    @Size(min = 6)
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/]).+$",
            message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 각각 최소 1글자 이상 포함해야 합니다."
    )
    private String password;

}