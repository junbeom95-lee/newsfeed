package com.newsfeed.cider.domain.community.service;

import com.newsfeed.cider.common.entity.Community;
import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.community.model.dto.CommunityDto;
import com.newsfeed.cider.domain.community.model.request.CreateCommunityRequest;
import com.newsfeed.cider.domain.community.model.response.CreateCommunityResponse;
import com.newsfeed.cider.domain.community.repository.CommunityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;


    public CommonResponse<CreateCommunityResponse> create(CreateCommunityRequest request) {

        Community community = new Community(request.getCommunityName(), request.getInfo());

        Community saved = communityRepository.save(community);

        CommunityDto dto = CommunityDto.from(saved);

        return new CommonResponse<>(HttpStatus.CREATED, CreateCommunityResponse.from(dto));
    }

    //TODO 그룹 생성
    //TODO Param CreateCommunityRequest request (communityName, info)
    //TODO Return CommonResponse<CreateCommunityResponse> (communityId, communityName, info)


    //TODO 그룹 조회 (PathVariable 있으면 선택 없으면 ALL)
    //TODO Param String communityName
    //TODO communityName == null -> ALL
    //TODO communityName != null -> one
    //TODO Return CommonResponse<List<GetCommunityResponse>> (communityId, communityName, info, createdAt, modifiedAt)


    //TODO 그룹 수정
    //TODO Param String communityName, UpdateCommunityRequest request (communityName, info)
    //TODO ResponseBody UpdateCommunityResponse (communityId, communityName, info)
    //TODO Return CommonResponse<UpdateCommunityResponse> (communityId, communityName, info, createdAt, modifiedAt)


    //TODO 그룹 삭제
    //TODO Method : DELETE, URL : "/community"
    //TODO Param String communityName
    //TODO Return CommonResponse<Void>
}
