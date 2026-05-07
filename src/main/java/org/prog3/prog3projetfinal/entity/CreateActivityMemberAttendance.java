package org.prog3.prog3projetfinal.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateActivityMemberAttendance {
    private String memberIdentifier;
    private AttendanceStatus attendanceStatus;
}

