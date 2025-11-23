package com.newsfeed.cider.domain.post.service;

import com.newsfeed.cider.common.entity.Community;
import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.domain.post.model.request.PostCreateRequest;
import com.newsfeed.cider.domain.post.model.response.PostCreateResponse;
import com.newsfeed.cider.domain.post.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostCreateResponse savePost(@Valid PostCreateRequest request, Long loginId) {

        validateLogin(loginId);

        Profile profile = getProfileById(loginId);

        Community community = null;
        if (request.getCommunityId() != null) {
            community = getCommunityById(request.getCommunityId());
        }

        Post post = new Post(
                profile,
                request.getTitle(),
                request.getContent(),
                community
        );

        Post savedPost = postRepository.save(post);
        return PostCreateResponse.from(savedPost);
    }

    // login 여부 검증
    private void validateLogin(Long loginUserId) {
        if (loginUserId == null) {
            throw new CustomException(ExceptionCode.FORBIDDEN);
        }
    }

    // 아직 구현되지 않은 메서드 , CommunityService에 구현 예정, 컴파일 에러 방지를 위해 선언만 해둠
    private Community getCommunityById(Long communityId) {
        return null;
    }

    // 아직 구현되지 않은 메서드 , ProfileService에 구현 예정, 컴파일 에러 방지를 위해 선언만 해둠
    private Profile getProfileById(Long loginUserId) {
        throw new CustomException(ExceptionCode.FORBIDDEN);
    }
}
