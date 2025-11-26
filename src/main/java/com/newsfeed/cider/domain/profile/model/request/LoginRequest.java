package com.newsfeed.cider.domain.profile.model.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Email
    private String email;
    private String password;
}