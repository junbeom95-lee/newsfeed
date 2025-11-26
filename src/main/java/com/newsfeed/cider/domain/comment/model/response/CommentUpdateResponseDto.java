package com.newsfeed.cider.domain.comment.model.response;

import com.newsfeed.cider.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Optional;  // 추가
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateResponseDto {
    private Long commentId;
    private String content;
    private String postTitle;
    private String postContent;
    private String profileName;
    private String communityName;
    private LocalDateTime modifiedAt;
    private List<CommentGetResponse> childrenCommentList;   //대댓글



    public static CommentUpdateResponseDto from(Comment comment, List<Comment> childrenCommentList) {
        List<CommentGetResponse> childrenDtos = childrenCommentList.stream()
                .map(CommentGetResponse::from)
                .collect(Collectors.toList());


        return new CommentUpdateResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getPost().getTitle(),
                comment.getPost().getContent(),
                comment.getProfile().getName(),
                comment.getPost().getCommunity().getCommunityName(),
                comment.getModifiedAt(),
                childrenDtos
        );

    }
}