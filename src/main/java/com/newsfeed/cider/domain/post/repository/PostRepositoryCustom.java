package com.newsfeed.cider.domain.post.repository;

import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.domain.post.model.condition.PostSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> search(PostSearchCond cond, Pageable pageable);
}
