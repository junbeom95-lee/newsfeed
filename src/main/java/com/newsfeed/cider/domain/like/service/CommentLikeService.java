package com.newsfeed.cider.domain.like.service;

import com.newsfeed.cider.domain.like.repository.CommentLikeRepository;
import com.newsfeed.cider.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final ProfileRepository profileRepository;
}
