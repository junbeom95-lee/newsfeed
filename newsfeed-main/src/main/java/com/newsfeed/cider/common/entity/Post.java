package com.newsfeed.cider.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;                //게시글 고유 ID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;            //게시글 작성 프로필 고유 ID

    @Column(nullable = false, length = 100)
    private String title;               //게시글 제목

    @Column(nullable = false)
    private String content;             //게시글 내용

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();  //댓글 리스트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;        //커뮤니티 (그룹)

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;    //삭제 여부 및 시점


    public Post(String content, String title, Profile profile, Community community) {
        this.content = content;
        this.title = title;
        this.profile = profile;
        this.community = community;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

}
