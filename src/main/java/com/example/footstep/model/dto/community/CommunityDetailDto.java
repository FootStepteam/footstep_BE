package com.example.footstep.model.dto.community;

import com.example.footstep.model.dto.comment.CommentResponseDto;
import com.example.footstep.model.entity.Comment;
import com.example.footstep.model.entity.Community;
import com.example.footstep.model.entity.Member;
import com.example.footstep.model.entity.ShareRoom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommunityDetailDto {

    private String memberNickname;
    private Long memberId;
    private Long communityId;
    private String communityName;
    private int likeCount;
    private LocalDateTime createdDate;
    private String content;
    private String travelStartDate;
    private String travelEndDate;
    private boolean isLiked;
    private int commentCount;
    private List<CommentResponseDto> comments = new ArrayList<>();


    public static CommunityDetailDto of(Community community, Member member,
        ShareRoom shareRoom, List<Comment> comments, boolean isLiked) {

        return CommunityDetailDto.builder()
            .communityId(community.getCommunityId())
            .communityName(community.getCommunityName())
            .likeCount(community.getLikeCount())
            .createdDate(community.getCreateDate())
            .content(community.getContent())
            .memberId(member.getMemberId())
            .memberNickname(member.getNickname())
            .travelStartDate(shareRoom.getTravelStartDate())
            .travelEndDate(shareRoom.getTravelEndDate())
            .commentCount(comments.size())
            .isLiked(isLiked)
            .comments(comments.stream()
                .map(CommentResponseDto::of)
                .collect(Collectors.toList())
            )
            .build();
    }
}
