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
    private Long profileCommunityId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "community_id")
    private Community community;


    @Column(name = "joined_at")
    private LocalDateTime joinedAt;


    public Profile_Community(Profile profile, Community community) {
        this.profile = profile;
        this.community = community;
        this.joinedAt = LocalDateTime.now();

    }

}
