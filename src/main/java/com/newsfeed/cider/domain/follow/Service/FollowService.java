package com.newsfeed.cider.domain.follow.Service;

import com.newsfeed.cider.common.entity.Follow;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.enums.FollowStatus;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.follow.model.response.FollowRequestResponse;
import com.newsfeed.cider.domain.follow.model.response.FollowResponse;
import com.newsfeed.cider.domain.follow.repository.FollowRepository;
import com.newsfeed.cider.domain.profile.model.response.SummaryProfileResponse;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
// - Properties
    private final FollowRepository followRepository;
    private final ProfileRepository profileRepository;

    // - Follow(Create)
    @Transactional
    public FollowResponse follow(Long followerId, Long followeeId) {
        // - Check Self Allow
        if (followerId.equals(followeeId)) {
            throw new CustomException(ExceptionCode.SELF_FOLLOW_NOT_ALLOWED);
        }
        // - Find Profile(Follower, Followee) By ID
        Profile follower = findProfileById(followerId);
        Profile followee = findProfileById(followeeId);
        // - Check Already Follow
        if (followRepository.existsByFollowerAndFolloweeAndStatus(
                follower, followee, FollowStatus.ACCEPTED)) {
            throw new CustomException(ExceptionCode.ALREADY_FOLLOW);
        }

        // - Check PrivateProfile
//        Follow follow;
//        if (followee.isPrivate()) {
//            follow = Follow.createRequested(follower, followee);
//        } else {
//            follow = Follow.createAccepted(follower, followee);
//        }

        // - Save New Follow
        Follow follow = new Follow(follower, followee, FollowStatus.ACCEPTED);
        followRepository.save(follow);

        // - return
        return FollowResponse.from(follow);
    }
    // - UnFollow(Delete)
    @Transactional
    public FollowResponse unfollow(Long followerId, Long followeeId) {
        // - Find Profile(Follower, Followee) By ID
        Profile follower = findProfileById(followerId);
        Profile followee = findProfileById(followeeId);
        // - Find Follow, Check Not Found Follow
        Follow follow = followRepository
                .findByFollowerAndFolloweeAndStatus(follower, followee, FollowStatus.ACCEPTED)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_FOLLOW));
        // - Delete Follow
        followRepository.delete(follow);
        // - Return
        return FollowResponse.from(follow);
    }
    // - GetFollowingList(Read)
    @Transactional(readOnly = true)
    public List<SummaryProfileResponse> getFollowingList(Long followerId) {
        // - Find Follower By ID
        Profile follower = findProfileById(followerId);
        // - Find All By Follower
        List<Follow> followers = followRepository
                .findAllByFollowerAndStatus(follower, FollowStatus.ACCEPTED);
        // - Return
        return followers.stream()
                .map(f -> SummaryProfileResponse.from(f.getFollowee()))
                .toList();
    }
    // - GetFolloweeList(Read)
    @Transactional(readOnly = true)
    public List<SummaryProfileResponse> getFollowerList(Long followeeId) {
        // - Find Followee By ID
        Profile followee = findProfileById(followeeId);
        // - Find All By Followee
        List<Follow> followees = followRepository
                .findAllByFolloweeAndStatus(followee, FollowStatus.ACCEPTED);
        // - Return
        return followees.stream()
                .map(f -> SummaryProfileResponse.from(f.getFollower()))
                .toList();
    }
    // - GetFollowRequestList(isPrivateProfile)
    @Transactional(readOnly = true)
    public List<FollowRequestResponse> getFollowRequestList(Long followerId) {
        // - Find Follower By ID
        Profile follower = findProfileById(followerId);
        // - Find All By FolloRequest
        List<Follow> requests = followRepository.findAllByFolloweeAndStatus(follower, FollowStatus.REQUESTED);
        // - Return
        return requests.stream()
                .map(FollowRequestResponse::from)
                .toList();
    }
    // - ApproveFollowRequest
    @Transactional
    public FollowResponse approveFollowRequest(Long loginUserId, Long followId) {
        // - Find LoginProfile, Follow Request
        Profile me = findProfileById(loginUserId);
        Follow follow = followRepository.findById(followId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_FOLLOW));

        // - Check My Request
        if (!follow.getFollowee().getProfileId().equals(me.getProfileId())) {
            throw new CustomException(ExceptionCode.FORBIDDEN);
        }
        if (follow.getStatus() != FollowStatus.REQUESTED) {
            throw new CustomException(ExceptionCode.NOT_FOUND_FOLLOW);
        }
        // - Follow Approve
        follow.approve();
        // - Return
        return FollowResponse.from(follow);
    }
    // - RejectFollowRequest
    @Transactional
    public FollowResponse rejectFollowRequest(Long loginUserId, Long followId) {
        // - Find LoginProfile, Follow Request
        Profile me = findProfileById(loginUserId);
        Follow follow = followRepository.findById(followId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_FOLLOW));

        // - Check My Request
        if (!follow.getFollowee().getProfileId().equals(me.getProfileId())) {
            throw new CustomException(ExceptionCode.FORBIDDEN);
        }
        if (follow.getStatus() != FollowStatus.REQUESTED) {
            throw new CustomException(ExceptionCode.NOT_FOUND_FOLLOW);
        }
        // - Follow Reject(Hard Delete)
        followRepository.delete(follow);
        // - Return
        return FollowResponse.from(follow);
    }
    // - Get Isfollowed
    @Transactional
    public boolean getIsFollowed(Long loginUserId, Long followedId) {
        Profile me = findProfileById(loginUserId);
        Profile followee = findProfileById(followedId);

        return followRepository.existsByFollowerAndFolloweeAndStatus(me, followee, FollowStatus.ACCEPTED);
    }


    // - Find Profile By ID
    private Profile findProfileById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow( () -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));
    }
}
