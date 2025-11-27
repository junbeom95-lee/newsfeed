package com.newsfeed.cider.domain.comment.model.response;

import com.newsfeed.cider.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateResponse {

    private Long commentId; // 댓글 ID
    private String content; // 댓글 내용
    private String postTitle; // 댓글이 달린 게시글 제목
    private String postContent; // 댓글이 달린 게시글 내용
    private String profileName; // 댓글 작성자 프로필 이름
    private Long likeCount;
    private LocalDateTime modifiedAt; // 댓글 수정 시간

    public static CommentUpdateResponse from(Comment comment) { // Comment 엔티티 → DTO 변환
        return new CommentUpdateResponse(
                comment.getCommentId(), // 댓글 ID
                comment.getContent(), // 댓글 내용
                comment.getPost().getTitle(), // 게시글 제목
                comment.getPost().getContent(), // 게시글 내용
                comment.getProfile().getName(), // 작성자 이름
                comment.getLikeCount(),
                comment.getModifiedAt() // 수정 시간
        );
    }
}
