package com.example.footstep.controller;

import com.example.footstep.component.security.LoginMember;
import com.example.footstep.domain.dto.schedule.DayScheduleDto;
import com.example.footstep.domain.dto.schedule.DayScheduleMemoDto;
import com.example.footstep.domain.form.DayScheduleForm;
import com.example.footstep.service.ScheduleService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share-room")
public class ScheduleController {

    private final ScheduleService scheduleService;


    @GetMapping("/{shareId}/schedule")
    public ResponseEntity<List<DayScheduleDto>> getAllListSchedule(
        @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(scheduleService.getAllListSchedule(shareId));
    }


    @GetMapping("/{shareId}/schedule/plan")
    public ResponseEntity<DayScheduleDto> getAllListScheduleDate(
        @PathVariable("shareId") Long shareId, @RequestParam("date") String planDate) {

        return ResponseEntity.ok(scheduleService.getAllListScheduleDate(shareId, planDate));
    }


    @PostMapping("/{shareId}/schedule")
    public ResponseEntity<DayScheduleMemoDto> createOrUpdateScheduleMemo(
        @AuthenticationPrincipal LoginMember loginMember, @PathVariable("shareId") Long shareId,
        @RequestBody @Valid DayScheduleForm dayScheduleForm) {

        return ResponseEntity.ok(
            scheduleService.createOrUpdateScheduleMemo(loginMember, shareId, dayScheduleForm));
    }
}