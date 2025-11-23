package com.newsfeed.cider.domain.post.service;

import com.newsfeed.cider.common.entity.Community;
import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.common.entity.Profile;
import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.domain.post.model.request.PostCreateRequest;
import com.newsfeed.cider.domain.post.model.request.PostUpdateRequest;
import com.newsfeed.cider.domain.post.model.response.PostCreateResponse;
import com.newsfeed.cider.domain.post.model.response.PostUpdateResponse;
import com.newsfeed.cider.domain.post.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.newsfeed.cider.common.enums.ExceptionCode.FORBIDDEN;
import static com.newsfeed.cider.common.enums.ExceptionCode.NOT_FOUND_POST;

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

    @Transactional
    public PostUpdateResponse updateService(@Valid PostUpdateRequest request, Long loginId) {

        validateLogin(loginId);

        Post post = postRepository.findByPostId(loginId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_POST));

        validateAuthorization(loginId, post.getProfile().getProfileId());

        // 제목 수정
        if (request.getTitle() != null) {
            post.updatePostTitle(request.getTitle());
        }

        // 내용 수정
        if (request.getContent() != null) {
            post.updatePostContent(request.getContent());
        }

        return PostUpdateResponse.from(post);
    }

    // login 여부 검증
    private void validateLogin(Long loginUserId) {
        if (loginUserId == null) {
            throw new CustomException(ExceptionCode.FORBIDDEN);
        }
    }

    // login한 profile이 권한을 가지고 있는지 검증
    public void validateAuthorization(Long loginUserId, Long userId) {
        boolean isSameUser = userId.equals(loginUserId);
        if (!isSameUser) {
            throw new CustomException(FORBIDDEN);
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
