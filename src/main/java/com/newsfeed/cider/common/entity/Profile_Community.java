package com.newsfeed.cider.common.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Entity
@Table( name = "profile_community",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_profile_community",
                        columnNames = {"profile_id", "community_id"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile_Community {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_community_id")
    private Long profileCommunityId;    //프로필_그룹 고유 ID

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "profile_id")
    private Profile profile;            //프로필 고유 ID : 좋아요 누른 사람

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "community_id")
    private Community community;        //그룹 고유 ID : 좋아요 눌러진 그룹


    @Column(name = "joined_at")
    private LocalDateTime joinedAt;     //좋아요한 시간


    public Profile_Community(Profile profile, Community community) {
        this.profile = profile;
        this.community = community;
        this.joinedAt = LocalDateTime.now();

    }

}
