package com.newsfeed.cider.domain.community.service;

import com.newsfeed.cider.common.entity.Community;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.community.model.dto.CommunityDto;
import com.newsfeed.cider.domain.community.model.request.CreateCommunityRequest;
import com.newsfeed.cider.domain.community.model.response.CreateCommunityResponse;
import com.newsfeed.cider.domain.community.model.response.GetCommunityResponse;
import com.newsfeed.cider.domain.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;

    /**
     * 그룹 생성
     * @param request CreateCommunityRequest (communityName, info)
     * @return CommonResponse<CreateCommunityResponse> (communityId, communityName, info, createdAt)
     * @throws CustomException EXIST_COMMUNITY
     */
    public CommonResponse<CreateCommunityResponse> create(CreateCommunityRequest request) {

        boolean existence = communityRepository.existsByCommunityName(request.getCommunityName());

        if(existence) throw new CustomException(ExceptionCode.EXIST_COMMUNITY);

        Community community = new Community(request.getCommunityName(), request.getInfo());

        Community saved = communityRepository.save(community);

        CommunityDto dto = CommunityDto.from(saved);

        return new CommonResponse<>(HttpStatus.CREATED, CreateCommunityResponse.from(dto));
    }

    /**
     * 그룹 조회 페이징
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return PagedModel<GetCommunityResponse> (communityId, communityName, info, createdAt)
     */
    @Transactional(readOnly = true)
    public CommonResponse<PagedModel<GetCommunityResponse>> getCommunityPage(int page, int size) {

        Sort sort = Sort.by("communityName").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Community> communityPage = communityRepository.findAll(pageable);

        Page<CommunityDto> dtoPage = communityPage.map(CommunityDto::from);

        Page<GetCommunityResponse> responsePage = dtoPage.map(GetCommunityResponse::from);

        PagedModel<GetCommunityResponse> response = new PagedModel<>(responsePage);

        return new CommonResponse<>(HttpStatus.OK, response);
    }

    //TODO 그룹 수정
    //TODO Param String communityName, UpdateCommunityRequest request (communityName, info)
    //TODO ResponseBody UpdateCommunityResponse (communityId, communityName, info)
    //TODO Return CommonResponse<UpdateCommunityResponse> (communityId, communityName, info, createdAt, modifiedAt)


    //TODO 그룹 삭제
    //TODO Method : DELETE, URL : "/community"
    //TODO Param String communityName
    //TODO Return CommonResponse<Void>
}
