package com.newsfeed.cider.common.util;

import com.newsfeed.cider.common.exception.CustomException;

import static com.newsfeed.cider.common.enums.ExceptionCode.ACCESS_DENIED;
import static com.newsfeed.cider.common.enums.ExceptionCode.NOT_LOGGED_IN;

public class AuthManager {

    // 로그인 여부 확인
    public static void validateLogin(Long loginId) {
        if (loginId == null) {
            throw new CustomException(NOT_LOGGED_IN);
        }
    }

    // 로그인한 계정이 권한이 있는지 확인
    public static void validateAuthorization(Long loginId, Long profileId) {
        boolean isSameUser = profileId.equals(loginId);
        if (!isSameUser) {
            throw new CustomException(ACCESS_DENIED);
        }
    }
}
