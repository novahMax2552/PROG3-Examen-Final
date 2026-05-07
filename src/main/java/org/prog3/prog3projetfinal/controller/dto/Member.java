package org.prog3.prog3projetfinal.controller.dto;

import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Member {
    private String id;                   // MemberIdentifier
    private String firstName;
    private String lastName;
    private String birthDate;            // format: date
    private String gender;               // enum: MALE/FEMALE
    private String address;
    private String profession;
    private Integer phoneNumber;
    private String email;
    private String occupation;           // enum: JUNIOR, SENIOR, etc.
    private List<Member> referees;       // liste des membres référents
}
