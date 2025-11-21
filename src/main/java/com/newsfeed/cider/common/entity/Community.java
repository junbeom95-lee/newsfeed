package com.newsfeed.cider.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "community")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;       //커뮤니티 고유 ID

    @Column(name = "community_name", nullable = false, unique = true, length = 50)
    private String communityName;   //커뮤니티 이름

    private String info;            //커뮤니티 설명

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Community(String communityName, String info) {
        this.communityName = communityName;
        this.info = info;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

}
