package com.newsfeed.cider.domain.follow.Service;

import com.newsfeed.cider.common.entity.Follow;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.enums.FollowStatus;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.domain.follow.model.response.FollowRequestResponse;
import com.newsfeed.cider.domain.follow.model.response.FollowResponse;
import com.newsfeed.cider.domain.follow.repository.FollowRepository;
import com.newsfeed.cider.domain.profile.model.response.SummaryProfileResponse;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
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
        Follow follow;
        if (followee.getIsPrivate()) {
            follow = Follow.createRequested(follower, followee);
        } else {
            follow = Follow.createAccepted(follower, followee);
        }
        // - Save New Follow
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
    // - GetAll Follows
    @Transactional(readOnly = true)
    public List<FollowResponse> getAll() {
        List<Follow> follows = followRepository.findAll();

        return follows.stream()
                .map(FollowResponse::from)
                .toList();
    }
    // - Get MyFollowingList(Read)
    @Transactional(readOnly = true)
    public List<SummaryProfileResponse> getMyFollowingList(Long loginUserId) {
        // - Find MyProfile By ID
        Profile me = findProfileById(loginUserId);
        // - Find Follows By Follower(me)
        List<Follow> follows = followRepository
                .findAllByFollowerAndStatus(me, FollowStatus.ACCEPTED);
        // - Return
        return follows.stream()
                .map(f -> SummaryProfileResponse.from(f.getFollowee()))
                .toList();
    }
    // - Get MyFolloweeList(Read)
    @Transactional(readOnly = true)
    public List<SummaryProfileResponse> getMyFollowerList(Long loginUserId) {
        // - Find MyProfile By ID
        Profile me = findProfileById(loginUserId);
        // - Find Follows By Followee(me)
        List<Follow> follows = followRepository
                .findAllByFolloweeAndStatus(me, FollowStatus.ACCEPTED);
        // - Return
        return follows.stream()
                .map(f -> SummaryProfileResponse.from(f.getFollower()))
                .toList();
    }
    // - Get Other FolloweeList
    @Transactional(readOnly = true)
    public List<SummaryProfileResponse> getOtherFollowingList(Long loginUserId, Long followerId) {
        // - Check Not Self
        if (followerId.equals(loginUserId)) { throw new CustomException(ExceptionCode.FORBIDDEN); }
        // - Find My Profile & Other Profile
        Profile me = findProfileById(loginUserId);
        Profile follower = findProfileById(followerId);
        // - If Private Profile, FollowCheck
        if (follower.getIsPrivate()) {
            if (!followRepository.existsByFollowerAndFolloweeAndStatus(me, follower, FollowStatus.ACCEPTED)) {
                return null;
            }
        }
        // - Find Follows By Follower
        List<Follow> follows = followRepository
                .findAllByFollowerAndStatus(follower, FollowStatus.ACCEPTED);
        // - Return
        return follows.stream()
                .map(f -> SummaryProfileResponse.from(f.getFollowee()))
                .toList();
    }
    // - Get Other FollowerList
    @Transactional(readOnly = true)
    public List<SummaryProfileResponse> getOtherFollowerList(Long loginUserId, Long followeeId) {
        // - Check Not Self
        if (followeeId.equals(loginUserId)) { throw new CustomException(ExceptionCode.FORBIDDEN); }
        // - Find My Profile & Other Profile
        Profile me = findProfileById(loginUserId);
        Profile followee = findProfileById(followeeId);
        // - If Private Profile, FollowCheck
        if (followee.getIsPrivate()) {
            if (!followRepository.existsByFollowerAndFolloweeAndStatus(me, followee, FollowStatus.ACCEPTED)) {
                return null;
            }
        }
        // - Find Follows By Follower
        List<Follow> follows = followRepository
                .findAllByFolloweeAndStatus(followee, FollowStatus.ACCEPTED);
        // - Return
        return follows.stream()
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
