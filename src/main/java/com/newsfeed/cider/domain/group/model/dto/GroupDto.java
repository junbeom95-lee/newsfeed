package com.newsfeed.cider.domain.group.model.dto;

import com.newsfeed.cider.common.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    private Long groupId;
    private String groupName;
    private String info;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static GroupDto from(Group group) {
        return new GroupDto(
                group.getGroupId(),
                group.getGroupName(),
                group.getInfo(),
                group.getCreatedAt(),
                group.getModifiedAt(),
                group.getDeletedAt()
        );
    }
}
