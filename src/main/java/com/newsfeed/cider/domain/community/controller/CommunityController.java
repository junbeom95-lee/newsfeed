package com.newsfeed.cider.domain.community.controller;

import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.domain.community.model.request.CreateCommunityRequest;
import com.newsfeed.cider.domain.community.model.response.CreateCommunityResponse;
import com.newsfeed.cider.domain.community.model.response.GetCommunityResponse;
import com.newsfeed.cider.domain.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    /**
     * 커뮤니티 그룹 생성
     * @param request CreateCommunityRequest (communityName, info)
     * @return CreateCommunityResponse (communityId, communityName, info, createdAt)
     */
    @PostMapping()
    public ResponseEntity<CommonResponse<CreateCommunityResponse>> create(@RequestBody CreateCommunityRequest request) {

        CommonResponse<CreateCommunityResponse> result = communityService.create(request);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 그룹 조회 페이징
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return PagedModel<GetCommunityResponse>> (communityId, communityName, info, createdAt)
     */
    @GetMapping()
    public ResponseEntity<CommonResponse<PagedModel<GetCommunityResponse>>> getCommunityPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        CommonResponse<PagedModel<GetCommunityResponse>> result = communityService.getCommunityPage(page, size);

        return ResponseEntity.status(result.getStatus()).body(result);
    }


    //TODO 그룹 수정
    //TODO Method : PUT, URL : "/community"
    //TODO PathVariable String communityName, RequestBody(communityName, info)
    //TODO ResponseBody UpdateCommunityResponse (communityId, communityName, info)


    //TODO 그룹 삭제
    //TODO Method : DELETE, URL : "/community"
    //TODO PathVariable String communityName
    //TODO ResponseBody NO_CONTENT
}
