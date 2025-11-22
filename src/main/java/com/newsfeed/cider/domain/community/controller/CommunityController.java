package com.newsfeed.cider.domain.community.controller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.community.model.request.CreateCommunityRequest;
import com.newsfeed.cider.domain.community.model.response.CreateCommunityResponse;
import com.newsfeed.cider.domain.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    /**
     * 커뮤니티 그룹 생성
     * @param request CreateCommunityRequest (communityName, info)
     * @return CreateCommunityResponse (communityId, communityName, info)
     */
    @PostMapping()
    public ResponseEntity<CommonResponse<CreateCommunityResponse>> create(@RequestBody CreateCommunityRequest request) {

        CommonResponse<CreateCommunityResponse> result = communityService.create(request);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    //TODO 그룹 조회 (PathVariable 있으면 선택 없으면 ALL)
    //TODO Method : GET, URL : "/community"
    //TODO PathVariable (require false) String communityName
    //TODO ResponseBody List<GetCommunityResponse> (communityId, communityName, info)


    //TODO 그룹 수정
    //TODO Method : PUT, URL : "/community"
    //TODO PathVariable String communityName, RequestBody(communityName, info)
    //TODO ResponseBody UpdateCommunityResponse (communityId, communityName, info)


    //TODO 그룹 삭제
    //TODO Method : DELETE, URL : "/community"
    //TODO PathVariable String communityName
    //TODO ResponseBody NO_CONTENT
}
