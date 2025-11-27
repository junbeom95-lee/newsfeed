package com.newsfeed.cider.domain.community.service;

import com.newsfeed.cider.common.entity.Community;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.community.model.request.CommunityCreateRequest;
import com.newsfeed.cider.domain.community.model.request.CommunityUpdateRequest;
import com.newsfeed.cider.domain.community.model.response.CommunityCreateResponse;
import com.newsfeed.cider.domain.community.model.response.CommunityGetOneResponse;
import com.newsfeed.cider.domain.community.model.response.CommunityGetResponse;
import com.newsfeed.cider.domain.community.model.response.CommunityUpdateResponse;
import com.newsfeed.cider.domain.community.repository.CommunityRepository;
import com.newsfeed.cider.domain.post.repository.PostRepository;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {

    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;
    private final CommunityRepository communityRepository;

    /**
     * 그룹 생성
     * @param request CreateCommunityRequest (communityName, info)
     * @return CommonResponse<CreateCommunityResponse> (communityId, communityName, info, createdAt)
     * @throws CustomException EXIST_COMMUNITY
     */
    public CommonResponse<CommunityCreateResponse> create(Long userId, CommunityCreateRequest request) {

        boolean existence = communityRepository.existsByCommunityName(request.getCommunityName());

        if(existence) throw new CustomException(ExceptionCode.EXIST_COMMUNITY);

        Profile profile = profileRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PROFILE));

        Community community = new Community(request.getCommunityName(), request.getInfo(), profile);

        Community saved = communityRepository.save(community);

        return new CommonResponse<>(HttpStatus.CREATED, CommunityCreateResponse.from(saved));
    }

    /**
     * 그룹 조회 페이징
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return PagedModel<GetCommunityResponse> (communityId, communityName, info, createdAt)
     */
    @Transactional(readOnly = true)
    public CommonResponse<PagedModel<CommunityGetResponse>> getCommunityPage(int page, int size) {

        Sort sort = Sort.by("communityName").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Community> communityPage = communityRepository.findAll(pageable);

        Page<CommunityGetResponse> responsePage = communityPage.map(CommunityGetResponse::from);

        PagedModel<CommunityGetResponse> response = new PagedModel<>(responsePage);

        return new CommonResponse<>(HttpStatus.OK, response);
    }

    /**
     * 단건 그룹 조회
     * @param communityName 그룹 이름
     * @return (communityId, communityName, info, countPost, createdAt)
     */
    @Transactional(readOnly = true)
    public CommonResponse<CommunityGetOneResponse> getOneCommunity(String communityName) {

        Community community = communityRepository.findByCommunityName(communityName).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_COMMUNITY));

        long countPost = postRepository.countByCommunity_CommunityId(community.getCommunityId());

        CommunityGetOneResponse response = CommunityGetOneResponse.from(community, countPost);

        return new CommonResponse<>(HttpStatus.OK, response);
    }

    /**
     * 그룹 수정
     * @param userId 로그인된 아이디
     * @param communityName 그룹 이름
     * @param request UpdateCommunityRequest (communityName, info) 수정할 그룹 이름, 그룹 설명
     * @return UpdateCommunityResponse (communityId, communityName, info, createdAt)
     */
    public CommonResponse<CommunityUpdateResponse> update(Long userId, String communityName, CommunityUpdateRequest request) {

        Community community = communityRepository.findByCommunityName(communityName).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_COMMUNITY));

        if (Objects.equals(community.getProfile().getProfileId(), userId)) {

            community.update(request);

            CommunityUpdateResponse response = CommunityUpdateResponse.from(community);

            return new CommonResponse<>(HttpStatus.OK, response);
        }

        throw new CustomException(ExceptionCode.ACCESS_DENIED);
    }

    /**
     * 그룹 삭제
     * @param userId 로그인된 아이디
     * @param communityName 그룹 이름
     * @return CommonResponse<Void> OK, null
     */
    public CommonResponse<Void> delete(Long userId, String communityName) {

        Community community = communityRepository.findByCommunityName(communityName).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_COMMUNITY));

        if (Objects.equals(community.getProfile().getProfileId(), userId)) {

            community.softDelete();

            return new CommonResponse<>(HttpStatus.OK, null);
        }

        throw new CustomException(ExceptionCode.NOT_FOUND_COMMUNITY);
    }
}
