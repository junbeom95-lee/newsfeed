package com.newsfeed.cider.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postlike_id")
    private Long postLikeId;        //게시글좋아요 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;        //프로필 고유 ID : 게시글에 좋아요 누른 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;              //게시글 고유 ID : 좋아요된 게시글

    public PostLike(Profile profile, Post post) {
        this.profile = profile;
        this.post = post;
    }
}
