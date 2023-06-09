package com.example.footstep.model.form;

import com.example.footstep.model.entity.DaySchedule;
import com.example.footstep.model.entity.Destination;
import com.example.footstep.model.entity.ShareRoom;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationForm {

    @NotNull(message = "여행일자를 선택하세요.")
    private String planDate;
    @NotNull(message = "목적지를 선택하세요.")
    private String destinationCategoryCode;
    @NotNull(message = "목적지를 선택하세요.")
    private String destinationName;
    @NotNull(message = "목적지를 선택하세요.")
    private String destinationAddress;
    @NotNull(message = "목적지를 선택하세요.")
    private String lng;
    @NotNull(message = "목적지를 선택하세요.")
    private String lat;
    private int seq;


    public DaySchedule toEntityDaySchedule(ShareRoom shareRoom) {
        return DaySchedule.builder()
            .planDate(planDate)
            .shareRoom(shareRoom)
            .build();
    }


    public Destination toEntityDestination(DaySchedule daySchedule) {
        return Destination.builder()
            .destinationCategoryCode(destinationCategoryCode)
            .destinationName(destinationName)
            .destinationAddress(destinationAddress)
            .lng(lng)
            .lat(lat)
            .seq(seq)
            .daySchedule(daySchedule)
            .build();
    }
}
