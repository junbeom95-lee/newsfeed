package com.newsfeed.cider.domain.follow.Controller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.common.model.SessionUser;
import com.newsfeed.cider.domain.follow.Service.FollowService;
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
            @SessionAttribute(name = "loginUser") SessionUser loginUser,
            @PathVariable Long followeeId) {
        Long followerId = loginUser.getUserId();
        FollowResponse result = followService.follow(followerId, followeeId);

        CommonResponse<FollowResponse> response =
                new CommonResponse<>(HttpStatus.CREATED, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - UnFollow
    @DeleteMapping("/{followeeId}/unfollow")
    public ResponseEntity<CommonResponse<FollowResponse>> unfollow(
            @SessionAttribute(name = "longinUser") SessionUser loginUser,
            @PathVariable Long followeeId) {
        Long followerId = loginUser.getUserId();
        FollowResponse result = followService.unfollow(followerId, followeeId);

        CommonResponse<FollowResponse> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get My Following List
    @GetMapping("/me/following")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getMyFollowingList(
            @SessionAttribute(name = "loginUser") SessionUser loginUser) {
        Long loginUserId = loginUser.getUserId();
        List<SummaryProfileResponse> result = followService.getMyFollowingList(loginUserId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get My Follower List
    @GetMapping("/me/follower")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getMyFollowerList(
            @SessionAttribute(name = "loginUser") SessionUser loginUser) {
        Long loginUserId = loginUser.getUserId();
        List<SummaryProfileResponse> result = followService.getMyFollowerList(loginUserId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get AnotherProfile Following List
    @GetMapping("/{profileId}/following")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getOtherFollowingList(
            @SessionAttribute(name = "loginUser") SessionUser loginUser,
            @PathVariable Long profileId) {
        Long loginUserId = loginUser.getUserId();
        List<SummaryProfileResponse> result = followService.getOtherFollowingList(loginUserId, profileId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get AnotherProfile Follower List
    @GetMapping("/{profileId}/follower")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getOtherFollowerList(
            @SessionAttribute(name = "loginUser") SessionUser loginUser,
            @PathVariable Long profileId) {
        Long loginUserId = loginUser.getUserId();
        List<SummaryProfileResponse> result = followService.getOtherFollowerList(loginUserId, profileId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Check Followed
    @GetMapping("/{followeeId}/isFollow")
    public ResponseEntity<CommonResponse<Boolean>> getIsFollowed(
            @SessionAttribute(name = "loginUser") SessionUser loginUser,
            @PathVariable Long followeeId) {
        Long loginId = loginUser.getUserId();
        boolean result = followService.getIsFollowed(loginId, followeeId);

        CommonResponse<Boolean> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}