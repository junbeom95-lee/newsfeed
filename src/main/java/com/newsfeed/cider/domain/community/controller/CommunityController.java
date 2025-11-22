package com.newsfeed.cider.domain.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    //TODO 그룹 생성
    //TODO Method : POST, URL : "/community"
    //TODO RequestBody CreateCommunityRequest (communityName, info)
    //TODO ResponseBody CreateCommunityResponse (communityId, communityName, info)


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
