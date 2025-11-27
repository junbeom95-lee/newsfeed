package com.newsfeed.cider.domain.comment.model.response;

import com.newsfeed.cider.common.entity.Comment;
import com.newsfeed.cider.common.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateResponse {
    private Long CommentId;// 수정된 댓글의 ID
    private String content; // 수정된 댓글 내용
    private String postTitle;// 댓글이 달린 게시글 제목
    private String postContent;// 댓글이 달린 게시글 내용
    private String profileName;// 댓글 작성자 프로필 이름
    private Long parentId;// 부모 댓글 아이디
    private LocalDateTime createdAt;// 댓글 생성 일자
    /**
     * Comment 엔티티를 기반으로 CommentCreateResponse DTO 생성
     *
     * @param comment 수정된 Comment 엔티티
     * @return CommentCreateResponse DTO
     */
    public static CommentCreateResponse from(Comment comment) {

        return new CommentCreateResponse(
                comment.getCommentId(),
                comment.getPost().getTitle(),
                comment.getPost().getContent(),
                comment.getContent(),
                comment.getProfile().getName(),
                comment.getParentId(),
                comment.getCreatedAt());
    }
}
