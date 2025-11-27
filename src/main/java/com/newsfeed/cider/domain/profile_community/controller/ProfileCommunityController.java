package com.newsfeed.cider.domain.profile_community.controller;


import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.profile_community.model.response.CommunityMemberResponse;
import com.newsfeed.cider.domain.profile_community.model.response.UserCommunityResponse;
import com.newsfeed.cider.domain.profile_community.service.ProfileCommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileCommunityController {
    private final ProfileCommunityService profileCommunityService;

    //커뮤니티 가입
    @PostMapping("/community/{communityId}/join")
    public ResponseEntity<CommonResponse<Void>> joinCommunity(@PathVariable Long communityId,
                                                              @SessionAttribute(name = "loginId") Long userId){
        profileCommunityService.createJoin(userId, communityId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>(HttpStatus.CREATED, null));
    }

    //커뮤니티 탈퇴
    @DeleteMapping("/community/{communityId}/withdrawal")
    public ResponseEntity<CommonResponse<Void>> withdrawal(@PathVariable Long communityId,
                                                           @SessionAttribute(name = "loginId") Long userId){
        profileCommunityService.deleteJoin(userId, communityId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, null));

    }

    //커뮤니티 가입자 리스트
    @GetMapping("/community/{communityId}/members")
    public ResponseEntity<CommonResponse<List<CommunityMemberResponse>>> getMembers(@PathVariable Long communityId){
        List<CommunityMemberResponse> response = profileCommunityService.getCommunityMembers(communityId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, response));
    }

    //특정 사용자가 가입한 커뮤니티 리스트
    @GetMapping("/profile/{profileId}/communities")
    public ResponseEntity<CommonResponse<List<UserCommunityResponse>>> getUserCommunities(@PathVariable Long profileId){
        List<UserCommunityResponse> response = profileCommunityService.getCommunitysByProfile(profileId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, response));
    }
}
