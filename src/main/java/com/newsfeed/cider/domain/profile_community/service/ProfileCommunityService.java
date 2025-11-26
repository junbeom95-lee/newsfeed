package com.newsfeed.cider.domain.profile_community.service;

import com.newsfeed.cider.common.entity.Community;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.entity.Profile_Community;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.domain.community.repository.CommunityRepository;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import com.newsfeed.cider.domain.profile_community.repository.ProfileCommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ProfileCommunityService {

    private final ProfileCommunityRepository profileCommunityRepository;
    private final ProfileRepository profileRepository;
    private final CommunityRepository communityRepository;


    //커뮤니티 가입(조인 생성)
    public void createJoin(Long profileId, Long communityId) {

        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        Community community =  communityRepository.findById(communityId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_COMMUNITY));

        boolean isJoined = profileCommunityRepository.existsByProfileAndCommunity(profile, community);

        if(isJoined) {
            throw new CustomException(ExceptionCode.ALREADY_JOINED);
        }

        Profile_Community profilecommmunity = new Profile_Community(profile, community);

        profileCommunityRepository.save(profilecommmunity);
    }

    //커뮤니티 탈퇴(가입 취소)



    //커뮤니티에 가입한 사용자 목록



    //사용자가 가입한 커뮤니티 목록


    //내가 가입한 커뮤니티 목록

}
