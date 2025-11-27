package com.newsfeed.cider.domain.comment.model.response;

import com.newsfeed.cider.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * 댓글 조회 응답 DTO 클래스.
 * 댓글 엔티티(Comment)를 기반으로 클라이언트에게 반환할 데이터를 매핑합니다.
 * 게시글 제목, 내용, 프로필 이름 등의 관련 정보를 포함하여 상세 응답을 제공합니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetResponse {
    private Long commentId;//댓글ID
    private String content;//댓글내용
    private String postTitle;//해당 댓글이 속한 게시글의 제목.
    private String postContent;//해당 댓글이 속한 게시글의 내용.
    private String profileName;//댓글 작성자 이름
    private Long likeCount;
    private Long parentId; //부모댓글 ID
    private LocalDateTime createdAt; //댓글작성시간
    private LocalDateTime modifiedAt;//댓글수정시간

    public static CommentGetResponse from(Comment comment) {
        return new CommentGetResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getPost().getTitle(),
                comment.getPost().getContent(),
                comment.getProfile().getName(),
                comment.getLikeCount(),
                comment.getParentId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}