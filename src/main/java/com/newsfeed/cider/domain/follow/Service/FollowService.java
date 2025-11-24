package com.newsfeed.cider.domain.follow.Service;

import com.newsfeed.cider.common.entity.Follow;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
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
        if (followerId.equals(followeeId)) {
            throw new CustomException(ExceptionCode.SELF_FOLLOW_NOT_ALLOWED);
        }

        Profile follower = findProById(followerId);
        Profile followee = findProById(followeeId);

        if (followRepository.existsByFollowerAndFollowee(follower, followee)) {
            throw new CustomException(ExceptionCode.ALREADY_FOLLOW);
        }

        Follow follow = new Follow(follower, followee);
        followRepository.save(follow);

        return new FollowResponse(follower.getProfileId(), followee.getProfileId());
    }
    // - UnFollow(Delete)
    @Transactional
    public FollowResponse unfollow(Long followerId, Long followeeId) {
        Profile follower = findProById(followerId);
        Profile followee = findProById(followeeId);

        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_FOLLOW));

        followRepository.delete(follow);

        return new FollowResponse(follower.getProfileId(), followee.getProfileId());
    }
    // - GetFollowingList(Read)
    @Transactional(readOnly = true)
    public List<SummaryProfileResponse> getFollowingList(Long followerId) {
        Profile follower = findProById(followerId);

        List<Follow> follows = followRepository.findAllByFollower(follower);

        return follows.stream()
                .map(f -> new SummaryProfileResponse(
                        f.getFollowee().getProfileId(),
                        f.getFollowee().getName()
                ))
                .toList();
    }
    // - GetFolloweeList(Read)
    @Transactional(readOnly = true)
    public List<SummaryProfileResponse> getFollowerList(Long followeeId) {
        Profile followee = findProById(followeeId);

        List<Follow> follows = followRepository.findAllByFollowee(followee);

        return follows.stream()
                .map(f -> new SummaryProfileResponse(
                        f.getFollower().getProfileId(),
                        f.getFollower().getName()
                ))
                .toList();
    }

    // - Find Profile By Id
    private Profile findProById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow( () -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));
    }
}
