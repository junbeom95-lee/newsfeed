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
public class Group extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;               //그룹 고유 ID

    @Column(name = "group_name", nullable = false, unique = true)
    private String groupName;           //그룹 이름

    private String info;                //그룹 설명

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;    //삭제 여부 및 시점

    public Group(String groupName, String info) {
        this.groupName = groupName;
        this.info = info;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

}
