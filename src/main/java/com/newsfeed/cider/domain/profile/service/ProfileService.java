package com.newsfeed.cider.domain.profile.service;


import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.common.utils.PasswordEncoder;
import com.newsfeed.cider.domain.profile.model.dto.ProfileDto;
import com.newsfeed.cider.domain.profile.model.request.ProfileCreateRequest;
import com.newsfeed.cider.domain.profile.model.response.ProfileCreateResponse;
import com.newsfeed.cider.domain.profile.model.response.ProfileUpdateResponse;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;


    public ProfileCreateResponse createProfile(ProfileCreateRequest request){
        if (profileRepository.existsByEmail(request.getEmail())){
            throw new CustomException("EXIST_EMAIL");
        }

        Profile profile = new Profile(request.getProfilename(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        profileRepository.save(profile);
        ProfileDto dto = ProfileDto.from(profile);

        return ProfileCreateResponse.from(dto);

    }


    public ProfileUpdateResponse updateProfile(long)
}
