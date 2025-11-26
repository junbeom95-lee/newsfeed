package com.newsfeed.cider.common.enums;

import lombok.Getter;

@Getter
public enum ValidCode { //Valid 오류 메시지 Enum

    //프로필
    EMAIL_EMAIL("email", "Email", "이메일 형식이 아닙니다. 다시 확인해주세요"),
    NOT_BLANK_PROFILENAME("profilename", "NotBlank", "유저명을 적어주세요. 다시 확인해주세요"),
    SIZE_PROFILENAME("profilename", "Size", "유저명은 4자리를 넘을 수 없습니다"),
    NOT_BLANK_PASSWORD("password", "NotBlank", "비빌번호를 적어주세요. 다시 확인해주세요"),
    SIZE_PASSWORD("password", "Size", "비밀번호가 너무 짧습니다"),

    //게시글
    NOT_BLANK_POST_TITLE("title", "NotBlank", "제목을 작성하지 않았습니다."),
    NOT_BLANK_POST_CONTENT("content", "NotBlank","내용을 작성하지 않았습니다."),
    DATE_TIME_FORMAT_START_DATE("startDate", "DateTimeFormat", "시작 날짜를 입력해주세요"),
    DATE_TIME_FORMAT_END_DATE("endDate", "DateTimeFormat", "종료 날짜를 입력해주세요"),
    TYPE_MISMATCH_START_DATE("startDate", "typeMismatch", "시작 날짜를 다시 확인해주세요"),
    TYPE_MISMATCH_END_DATE("endDate", "typeMismatch", "종료 날짜를 다시 확인해주세요"),


    //댓글

    //팔로우

    //그룹
    NOT_BLANK_COMMUNITY_NAME("communityName", "NotBlank", "커뮤니티 이름을 적어주세요");

    private final String field;
    private final String code;
    private final String message;

    ValidCode(String field, String code, String message) {
        this.field = field;
        this.code = code;
        this.message = message;
    }

    /**
     * 필드이름과 어노테이션 이름으로 메시지 얻기
     * @param field 필드 이름
     * @param code  어노테이션 이름
     * @return 메시지
     */
    public static String getMessage(String field, String code) {
        for (ValidCode valid : values()) {
            if (valid.field.equals(field) && valid.code.equals(code)) {
                return valid.message;
            }
        }
        return "올바르지 않은 입력입니다";
    }

}

