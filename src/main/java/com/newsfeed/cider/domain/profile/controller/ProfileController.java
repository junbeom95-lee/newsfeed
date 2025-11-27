package com.newsfeed.cider.domain.profile.controller;


import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.profile.model.request.LoginRequest;
import com.newsfeed.cider.domain.profile.model.request.ProfileCreateRequest;
import com.newsfeed.cider.domain.profile.model.request.ProfileDeleteRequest;
import com.newsfeed.cider.domain.profile.model.request.ProfileUpdateRequest;
import com.newsfeed.cider.domain.profile.model.response.ProfileCreateResponse;
import com.newsfeed.cider.domain.profile.model.response.ProfileReadResponse;
import com.newsfeed.cider.domain.profile.model.response.ProfileUpdateResponse;
import com.newsfeed.cider.domain.profile.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    //프로필 정보 보기
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<CommonResponse<ProfileReadResponse>> getProfile(@PathVariable Long profileId,
                                                                          @SessionAttribute(name = "loginId", required = false) Long userId) {

        Long nowLoginProfileId = (userId != null) ? userId : null;

        ProfileReadResponse response = profileService.getProfile(profileId, nowLoginProfileId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, response));
    }

    //전체 유저 조회(탈퇴 사용자 제외)
    @GetMapping("/profiles")
    public ResponseEntity<CommonResponse<List<ProfileReadResponse>>> getAllProfiles(){
        List<ProfileReadResponse> response = profileService.getAllProfiles();

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, response));
    }

    //정보 수정
    @PutMapping("/profile/{profileId}")
    public ResponseEntity<CommonResponse<ProfileUpdateResponse>> updateProfile(@PathVariable Long profileId,
                                                                               @SessionAttribute(name = "loginId") Long userId,
                                                                               @RequestBody ProfileUpdateRequest request){

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, profileService.updateProfile(userId, profileId, request)));
    }

    //계정 공개 설정
    @PutMapping("/profile/{profileId}/public")
    public ResponseEntity<CommonResponse<ProfileUpdateResponse>> updateProfilePublic(@PathVariable Long profileId,
                                                                               @SessionAttribute(name = "loginId") Long userId){

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, profileService.updateProfilePublic(userId, profileId)));
    }

    //계정 비공개 설정
    @PutMapping("/profile/{profileId}/private")
    public ResponseEntity<CommonResponse<ProfileUpdateResponse>> updateProfilePrivate(@PathVariable Long profileId,
                                                                               @SessionAttribute(name = "loginId") Long userId){


        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, profileService.updateProfilePrivate(userId, profileId)));
    }



    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<ProfileCreateResponse>> createProfile(@Valid @RequestBody ProfileCreateRequest request){
        ProfileCreateResponse response = profileService.createProfile(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>(HttpStatus.CREATED, response));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Void>> login(@RequestBody LoginRequest request, HttpSession session){

        Long userId = profileService.login(request);

        session.setAttribute("loginId", userId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, null));

    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(HttpSession session){

        if(session.getAttribute("loginId") == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse<>(HttpStatus.BAD_REQUEST, null));
        }

        session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CommonResponse<>(HttpStatus.NO_CONTENT, null));

    }

    //회원탈퇴
    @DeleteMapping("/profile/{profileId}")
    public ResponseEntity<CommonResponse<Void>> deleteProfile(
            @PathVariable Long profileId,
            @Valid @RequestBody ProfileDeleteRequest request,
            HttpSession session){

        Long userId = (Long) session.getAttribute("loginId");

        profileService.deleteProfile(userId, profileId, request);

        session.invalidate();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, null));
    }



}
