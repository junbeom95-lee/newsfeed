package com.newsfeed.cider.domain.post.repository;

import com.newsfeed.cider.common.entity.Post;
import com.newsfeed.cider.domain.post.model.condition.PostSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.newsfeed.cider.common.entity.QPost.post;

@Repository
@AllArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Post> search(PostSearchCond cond, Pageable pageable) {

        List<Post> posts = jpaQueryFactory
                .selectFrom(post)
                .where(
                        titleContains(cond.getTitle()),
                        contentContains(cond.getContent()),
                        authorNameEquals(cond.getAuthorName()),
                        createdBetween(cond.getStartDate(), cond.getEndDate())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long postsCount = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(
                        titleContains(cond.getTitle()),
                        contentContains(cond.getContent()),
                        authorNameEquals(cond.getAuthorName()),
                        createdBetween(cond.getStartDate(), cond.getEndDate())
                )
                .fetchOne();

        return new PageImpl<>(posts, pageable, postsCount);
    }

    // 입력된 제목이 포함된 제목의 Post 조회
    private BooleanExpression titleContains(String title) {
        if (title == null || title.isBlank()) {
            return null;
        }
        return post.title.contains(title);
    }

    // 입력된 내용이 포함된 내용이 있는 Post 조회
    private BooleanExpression contentContains(String content) {
        if (content == null || content.isBlank()) {
            return null;
        }
        return post.content.contains(content);
    }

    // 입력된 이름과 동일한 작성자가 작성한 Post 조회
    private BooleanExpression authorNameEquals(String authorName) {
        if (authorName == null || authorName.isBlank()) {
            return null;
        }
        return post.profile.name.eq(authorName);
    }

    // 입력된 기간에 작성된 Post 조회
    private BooleanExpression createdBetween(
            java.time.LocalDateTime startDate,
            java.time.LocalDateTime endDate
    ) {
        if (startDate == null && endDate == null) {
            return null;
        }
        if (startDate != null && endDate != null) {
            return post.createdAt.between(startDate, endDate);
        }
        if (startDate != null) {
            return post.createdAt.goe(startDate);
        }
        return post.createdAt.loe(endDate);
    }
}
