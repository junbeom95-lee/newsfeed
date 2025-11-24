package com.newsfeed.cider.domain.profile.repository;

import com.newsfeed.cider.common.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
