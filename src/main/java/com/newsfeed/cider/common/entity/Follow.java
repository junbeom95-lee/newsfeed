package com.newsfeed.cider.common.entity;

import com.newsfeed.cider.common.enums.FollowStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table( name = "follows",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_follow_follower_followee",
                        columnNames = {"follower_id", "followee_id"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "follower_id", nullable = false)
    private Profile follower;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "followee_id", nullable = false)
    private Profile followee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FollowStatus status;

    public Follow(Profile follower, Profile followee, FollowStatus followStatus) {
        this.follower = follower;
        this.followee = followee;
        this.status = followStatus;
    }

    // - Create By FollowStatus
    public static Follow createRequested(Profile follower, Profile followee) {
        return new Follow(follower, followee, FollowStatus.REQUESTED);
    }
    public static Follow createAccepted(Profile follower, Profile followee) {
        return new Follow(follower, followee, FollowStatus.ACCEPTED);
    }
    // - Change FollowStatus
    public void approve() {
        this.status = FollowStatus.ACCEPTED;
    }
    public void reject() {
        this.status = FollowStatus.REJECTED;
    }
}
