package com.example.studyplatform.domain.calender;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.dto.calender.CalenderResponse;
import com.example.studyplatform.dto.calender.PutCalenderRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Calender extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // TODO: 유저아이디를 외래키로 관리해야하는가?

    private String calenderTitle;

    private String calenderContents;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean alarm;

    private boolean online;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @Builder
    public Calender(String calenderTitle,
                    String calenderContents,
                    LocalDateTime startDate,
                    LocalDateTime endDate,
                    LocalDateTime startTime,
                    LocalDateTime endTime,
                    boolean alarm,
                    boolean online,
                    Study study,
                    Long userId
    ) {
        this.calenderTitle = calenderTitle;
        this.calenderContents = calenderContents;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.alarm = alarm;
        this.online = online;
        this.status = Status.ACTIVE;
        this.study = study;
        this.userId = userId;
    }

    public CalenderResponse result(Set<Long> userIds) {
        return CalenderResponse.of(
                id,
                study.getId(),
                userId,
                calenderTitle,
                calenderContents,
                startDate,
                endDate,
                startTime,
                endTime,
                userIds,
                alarm,
                online,
                status
        );
    }

    public CalenderResponse update(PutCalenderRequest req) {
        this.calenderTitle = req.getCalenderTitle();
        this.calenderContents = req.getCalenderContents();
        this.startDate = req.getStartDate();
        this.endDate = req.getEndDate();
        this.startTime = req.getStartTime();
        this.endTime = req.getEndTime();
        this.alarm = req.isAlarm();
        this.online = req.isOnline();

        return CalenderResponse.of(
                this.id,
                this.study.getId(),
                this.userId,
                this.calenderTitle,
                this.calenderContents,
                this.startDate,
                this.endDate,
                this.startTime,
                this.endTime,
                req.getAttends(), // 참석자
                this.alarm,
                this.online,
                this.status
        );
    }

    public void inActive() {
        this.status = Status.INACTIVE;
    }

    public void active() {
        this.status = Status.ACTIVE;
    }
}
