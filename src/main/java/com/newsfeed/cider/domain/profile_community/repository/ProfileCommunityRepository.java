package com.newsfeed.cider.domain.profile_community.repository;

import com.newsfeed.cider.common.entity.Community;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.entity.Profile_Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ProfileCommunityRepository extends JpaRepository<Profile_Community, Long> {

    //커뮤니티에 가입한 전체 유저
    List<Profile_Community> findAllByCommunity(Community community);

    //유저가 가입한 전체 커뮤니티 조회
    List<Profile_Community> findAllByProfile(Profile profile);

    //유저의 커뮤니티 가입여부 확인
    boolean existsByProfileAndCommunity(Profile profile, Community community);

    //커뮤니티 가입 취소
    void deleteByProfileAndCommunity(Profile profile, Community community);

}
