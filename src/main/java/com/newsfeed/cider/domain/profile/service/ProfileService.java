package com.newsfeed.cider.domain.profile.service;


import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.enums.FollowStatus;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.common.model.SessionUser;
import com.newsfeed.cider.common.util.PasswordEncoder;
import com.newsfeed.cider.domain.profile.model.request.LoginRequest;
import com.newsfeed.cider.domain.profile.model.request.ProfileCreateRequest;
import com.newsfeed.cider.domain.profile.model.request.ProfileDeleteRequest;
import com.newsfeed.cider.domain.profile.model.request.ProfileUpdateRequest;
import com.newsfeed.cider.domain.profile.model.response.ProfileCreateResponse;
import com.newsfeed.cider.domain.profile.model.response.ProfileReadResponse;
import com.newsfeed.cider.domain.profile.model.response.ProfileUpdateResponse;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newsfeed.cider.domain.follow.repository.FollowRepository;

import java.util.List;

import static com.newsfeed.cider.common.util.AuthManager.validateAuthorization;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;


    public ProfileCreateResponse createProfile(ProfileCreateRequest request){
        if (profileRepository.existsByEmail(request.getEmail())){
            throw new CustomException(ExceptionCode.EXIST_EMAIL);
        }

        Profile profile = new Profile(request.getProfileName(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        profileRepository.save(profile);

        return ProfileCreateResponse.from(profile);

    }


    public ProfileUpdateResponse updateProfile(Long nowLoginProfileId, Long profileId, ProfileUpdateRequest request) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));


        validateAuthorization(nowLoginProfileId, profile.getProfileId());

        if(!passwordEncoder.matches(request.getPassword(), profile.getPassword())){
            throw new CustomException(ExceptionCode.WRONG_PASSWORD);
        }

        if(request.getProfileName() != null){
            profile.updateProfileName(request.getProfileName());
        }

        if(request.getEmail() != null && !request.getEmail().isBlank()){
            profile.updateProfileEmail(request.getEmail());
        }

        if(request.getNewPassword() != null){
            if(passwordEncoder.matches(request.getNewPassword(), profile.getPassword())){
                throw new CustomException(ExceptionCode.SAME_PASSWORD);
            }

            String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
            profile.updatePassword(encodedNewPassword);


        }
        profileRepository.save(profile);
        return ProfileUpdateResponse.from(profile);
    }

    public ProfileUpdateResponse updateProfilePrivate(Long nowLoginProfileId, Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        validateAuthorization(nowLoginProfileId, profile.getProfileId());

        profile.setPrivate(Boolean.TRUE);

        profileRepository.save(profile);
        return ProfileUpdateResponse.from(profile);
    }

    public ProfileUpdateResponse updateProfilePublic(Long nowLoginProfileId, Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        validateAuthorization(nowLoginProfileId, profile.getProfileId());

        profile.setPrivate(Boolean.FALSE);

        profileRepository.save(profile);
        return ProfileUpdateResponse.from(profile);
    }



    public void deleteProfile(Long nowLoginProfileId, Long profileId, ProfileDeleteRequest request){

        Profile profile =  profileRepository.findById(profileId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        validateAuthorization(nowLoginProfileId, profile.getProfileId());

        if(!passwordEncoder.matches(request.getPassword(), profile.getPassword())){
            throw new CustomException(ExceptionCode.WRONG_PASSWORD);
        }

        profile.softDelete();
        profileRepository.save(profile);
    }


    @Transactional(readOnly = true)
    public ProfileReadResponse getProfile(Long profileId, Long nowLoginProfileId){
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        //공개 계정
        if(!profile.getIsPrivate()){
            return ProfileReadResponse.from(profile);
        }

        //내 계정 조회
        if (nowLoginProfileId != null && nowLoginProfileId.equals(profileId)){
            return ProfileReadResponse.from(profile);
        }

        //로그인 x, 비공개 계정 조회
        if(nowLoginProfileId == null){
            throw new CustomException(ExceptionCode.FORBIDDEN);
        }

        //팔로우 여부 확인
        boolean isFollower = isFollow(nowLoginProfileId, profileId);

        if(!isFollower){
            throw new CustomException(ExceptionCode.FORBIDDEN);
        }
        return ProfileReadResponse.from(profile);

    }

    //전체 사용자 조회(탈퇴 사용자 제외)
    @Transactional(readOnly = true)
    public List<ProfileReadResponse> getAllProfiles(){
        List<Profile> profiles = profileRepository.findAllByDeletedAtIsNull();

        return profiles.stream().map(ProfileReadResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public SessionUser login(LoginRequest request){
        Profile profile = profileRepository.findByEmail(request.getEmail()).orElseThrow(() -> new CustomException(ExceptionCode.UN_AUTHORIZED));

        if(!passwordEncoder.matches(request.getPassword(), profile.getPassword())){
            throw new CustomException(ExceptionCode.UN_AUTHORIZED);
        }

        return new SessionUser(profile.getProfileId(), profile.getEmail());
    }

    @Transactional(readOnly = true)
    boolean isFollow(Long nowLoginProfileId, Long followeeId){

        Profile follower = profileRepository.findById(nowLoginProfileId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        Profile followee = profileRepository.findById(followeeId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        return followRepository.existsByFollowerAndFolloweeAndStatus(follower, followee, FollowStatus.ACCEPTED);
    }

}
