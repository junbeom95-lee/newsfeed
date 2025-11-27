package com.newsfeed.cider.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentlike_id")
    private Long commentLikeId;     //댓글좋아요 고유 ID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;        //프로필 고유 ID : 좋아요 누른 사람

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;        //댓글 고유 ID : 좋아요된 댓글

    public CommentLike(Profile profile, Comment comment) {
        this.profile = profile;
        this.comment = comment;
    }
}
