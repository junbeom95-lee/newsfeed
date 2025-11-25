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
@RequestMapping("/profiles")
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
    @GetMapping("/following")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getMyFollowingList(
            @SessionAttribute(name = "loginUser") SessionUser loginUser) {
        Long followerId = loginUser.getUserId();
        List<SummaryProfileResponse> result = followService.getFollowingList(followerId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get My Follower List
    @GetMapping("/follower")
    public ResponseEntity<CommonResponse<List<SummaryProfileResponse>>> getMyFollowerList(
            @SessionAttribute(name = "loginUser") SessionUser loginUser) {
        Long followeeId = loginUser.getUserId();
        List<SummaryProfileResponse> result = followService.getFollowerList(followeeId);

        CommonResponse<List<SummaryProfileResponse>> response =
                new CommonResponse<>(HttpStatus.OK, result);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    // - Get AnotherProfile Following List
    // - Get AnotherProfile Follower List
    // - Check Followed
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