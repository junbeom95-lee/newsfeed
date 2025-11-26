package com.newsfeed.cider.domain.profile_community.service;

import com.newsfeed.cider.common.entity.Community;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.entity.Profile_Community;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.domain.community.repository.CommunityRepository;
import com.newsfeed.cider.domain.profile.model.response.ProfileReadResponse;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import com.newsfeed.cider.domain.profile_community.model.response.CommunityMemberResponse;
import com.newsfeed.cider.domain.profile_community.model.response.UserCommunityResponse;
import com.newsfeed.cider.domain.profile_community.repository.ProfileCommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ProfileCommunityService {

    private final ProfileCommunityRepository profileCommunityRepository;
    private final ProfileRepository profileRepository;
    private final CommunityRepository communityRepository;


    //커뮤니티 가입(조인 생성)
    public void createJoin(Long profileId, Long communityId) {

        Profile profile = profileRepository.findById(profileId).orElseThrow(()
                -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        Community community =  communityRepository.findById(communityId).orElseThrow(()
                -> new CustomException(ExceptionCode.NOT_FOUND_COMMUNITY));

        boolean isJoined = profileCommunityRepository.existsByProfileAndCommunity(profile, community);

        if(isJoined) {
            throw new CustomException(ExceptionCode.ALREADY_JOINED);
        }

        Profile_Community profilecommmunity = new Profile_Community(profile, community);

        profileCommunityRepository.save(profilecommmunity);
    }

    //커뮤니티 탈퇴(가입 취소)
    public void deleteJoin(Long profileId, Long communityId) {

        Profile profile =  profileRepository.findById(profileId).orElseThrow(()
                -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));
        Community community = communityRepository.findById(communityId).orElseThrow(()
                -> new CustomException(ExceptionCode.NOT_FOUND_COMMUNITY));

        boolean isJoined = profileCommunityRepository.existsByProfileAndCommunity(profile, community);

        if(!isJoined) {
            throw new CustomException(ExceptionCode.NOT_JOINED);
        }

        profileCommunityRepository.deleteByProfileAndCommunity(profile, community);
    }

    //커뮤니티에 가입한 사용자 목록
    @Transactional(readOnly = true)
    public List<CommunityMemberResponse> getCommunityMembers(Long communityId) {
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_COMMUNITY));

        List<Profile_Community> joins = profileCommunityRepository.findAllByCommunity(community);

        return joins.stream().map(profileCommunity
                -> new CommunityMemberResponse(profileCommunity.getProfile().getProfileId(),
                profileCommunity.getProfile().getName(),
                profileCommunity.getJoinedAt())).toList();
    }



    //사용자가 가입한 커뮤니티 목록
    @Transactional(readOnly = true)
    public List<UserCommunityResponse> getCommunitysByProfile(Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        List<Profile_Community> joins = profileCommunityRepository.findAllByProfile(profile);

        return joins.stream().map(profileCommunity
                -> new UserCommunityResponse(
                        profileCommunity.getCommunity().getCommunityId(),
                profileCommunity.getCommunity().getCommunityName(),
                profileCommunity.getJoinedAt()
        )).toList();

    }


}
