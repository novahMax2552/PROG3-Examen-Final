package org.prog3.prog3projetfinal.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActivityMemberAttendance {
    private String id;
    private MemberDescription memberDescription;
    private AttendanceStatus attendanceStatus;
}

