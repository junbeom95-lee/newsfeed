package com.newsfeed.cider.common.entity;

import com.newsfeed.cider.domain.profile.model.request.ProfileUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;             //프로필 고유 ID

    @Column(nullable = false, length = 30)
    private String name;                //이름

    @Column(nullable = false, length = 50, unique = true)
    private String email;               //이메일

    @Column(nullable = false)
    private String password;            //비밀번호

    @Column(name = "is_private")
    private Boolean isPrivate = false;           //비공개 설정(기본값: 공개)

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;    //삭제 여부 및 시점

    public Profile(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void updateProfileInfo(String profilename, String email) {
        this.name = profilename;
        this.email = email;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }


    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
