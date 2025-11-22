package com.newsfeed.cider.domain.community.repository;

import com.newsfeed.cider.common.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community,Long> {

    boolean existsByCommunityName(String communityName);
}
