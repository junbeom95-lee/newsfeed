package com.newsfeed.cider.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;                //게시글 고유 ID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;            //게시글 작성 프로필 고유 ID

    @Column(nullable = false, length = 100)
    private String title;               //게시글 제목

    @Column(nullable = false)
    private String content;             //게시글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Group group;                //그룹 고유 ID

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;    //삭제 여부 및 시점

    public Post(Profile profile, String title, String content, Group group) {
        this.profile = profile;
        this.title = title;
        this.content = content;
        this.group = group;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

}
