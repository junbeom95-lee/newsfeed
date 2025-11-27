package com.newsfeed.cider.domain.follow.Controller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.follow.Service.FollowService;
import com.newsfeed.cider.domain.follow.model.response.FollowRequestResponse;
import com.newsfeed.cider.domain.follow.model.response.FollowResponse;
import com.newsfeed.cider.domain.profile.model.response.SummaryProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class FollowController {
// - Properties
    private final FollowService followService;

// - Methods
    // - Follow
    @PostMapping("/{followeeId}/follow")
    public ResponseEntity<CommonResponse<FollowResponse>> follow(
            @SessionAttribute(name = "loginId") Long userId,
            @PathVariable Long followeeId) {
        Long followerId = userId;
        FollowResponse result = followService.follow(followerId, followeeId);

        CommonResponse<FollowResponse> response =
                new CommonResponse<>(HttpStatus.CREATED, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - UnFollow
    @DeleteMapping("/{followeeId}/unfollow")
    public ResponseEntity<CommonResponse<FollowResponse>> unfollow(
            @SessionAttribute(name = "loginId") Long userId,
            @PathVariable Long followeeId) {
        Long loginUserId = userId;
        FollowResponse result = followService.unfollow(loginUserId, followeeId);

        CommonResponse<FollowResponse> response =
                new CommonResponse<>(HttpStatus.NO_CONTENT, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get AllFollows
    @GetMapping("/master/follows")
    public ResponseEntity<CommonResponse<List<FollowResponse>>> getFollows() {
        List<FollowResponse> result = followService.getAll();

        CommonResponse<List<FollowResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get My Following List
    @GetMapping("/me/following")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getMyFollowingList(
            @SessionAttribute(name = "loginId") Long userId) {
        Long loginUserId = userId;
        List<SummaryProfileResponse> result = followService.getMyFollowingList(loginUserId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get My Follower List
    @GetMapping("/me/follower")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getMyFollowerList(
            @SessionAttribute(name = "loginId") Long userId) {
        Long loginUserId = userId;
        List<SummaryProfileResponse> result = followService.getMyFollowerList(loginUserId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get AnotherProfile Following List
    @GetMapping("/{profileId}/following")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getOtherFollowingList(
            @SessionAttribute(name = "loginId") Long userId,
            @PathVariable Long profileId) {
        Long loginUserId = userId;
        List<SummaryProfileResponse> result = followService.getOtherFollowingList(loginUserId, profileId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get AnotherProfile Follower List
    @GetMapping("/{profileId}/follower")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getOtherFollowerList(
            @SessionAttribute(name = "loginId") Long userId,
            @PathVariable Long profileId) {
        Long loginUserId = userId;
        List<SummaryProfileResponse> result = followService.getOtherFollowerList(loginUserId, profileId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Check Followed
    @GetMapping("/{followeeId}/isFollow")
    public ResponseEntity<CommonResponse<Boolean>> getIsFollowed(
            @SessionAttribute(name = "loginId") Long userId,
            @PathVariable Long followeeId) {
        Long loginUserId = userId;
        boolean result = followService.getIsFollowed(loginUserId, followeeId);

        CommonResponse<Boolean> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get FollowRequestList
    @GetMapping("/me/followrequests")
    public ResponseEntity<CommonResponse<List<FollowRequestResponse>>> getFollowRequests(
            @SessionAttribute(name = "loginId") Long userId) {
        Long loginUserId = userId;
        List<FollowRequestResponse> result = followService.getFollowRequestList(loginUserId);

        CommonResponse<List<FollowRequestResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - FollowRequest Accept
    @PutMapping("/followrequest/{followId}/accept")
    public ResponseEntity<CommonResponse<FollowResponse>> acceptFollow(
            @SessionAttribute(name = "loginId") Long userId,
            @PathVariable Long followId) {
        Long loginUserId = userId;
        FollowResponse result = followService.approveFollowRequest(loginUserId, followId);

        CommonResponse<FollowResponse> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - FollowRequest Reject
    @PutMapping("/followrequest/{followId}/reject")
    public ResponseEntity<CommonResponse<FollowResponse>> rejectFollow(
            @SessionAttribute(name = "loginId") Long userId,
            @PathVariable Long followId) {
        Long loginUserId = userId;
        FollowResponse result = followService.rejectFollowRequest(loginUserId, followId);

        CommonResponse<FollowResponse> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}