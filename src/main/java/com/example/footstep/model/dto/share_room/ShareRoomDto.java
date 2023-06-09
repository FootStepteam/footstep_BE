package com.example.footstep.model.dto.share_room;

import com.example.footstep.model.entity.ShareRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareRoomDto {

    private Long shareId;
    private String shareName;
    private String shareCode;
    private String startPoint;
    private String endPoint;
    private String travelStartDate;
    private String travelEndDate;
    private String imageUrl;
    private boolean hostFlag;


    public static ShareRoomDto from(ShareRoom shareRoom) {
        return ShareRoomDto.builder()
            .shareId(shareRoom.getShareId())
            .shareName(shareRoom.getShareName())
            .shareCode(shareRoom.getShareCode())
            .startPoint(shareRoom.getStartPoint())
            .endPoint(shareRoom.getEndPoint())
            .travelStartDate(shareRoom.getTravelStartDate())
            .travelEndDate(shareRoom.getTravelEndDate())
            .imageUrl(shareRoom.getImageUrl())
            .build();
    }


    public static ShareRoomDto of(ShareRoom shareRoom, boolean hostFlag) {
        return ShareRoomDto.builder()
            .shareId(shareRoom.getShareId())
            .shareName(shareRoom.getShareName())
            .shareCode(shareRoom.getShareCode())
            .startPoint(shareRoom.getStartPoint())
            .endPoint(shareRoom.getEndPoint())
            .travelStartDate(shareRoom.getTravelStartDate())
            .travelEndDate(shareRoom.getTravelEndDate())
            .imageUrl(shareRoom.getImageUrl())
            .hostFlag(hostFlag)
            .build();
    }
}
