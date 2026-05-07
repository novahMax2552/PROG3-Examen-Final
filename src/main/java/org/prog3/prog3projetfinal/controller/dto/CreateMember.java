package org.prog3.prog3projetfinal.controller.dto;

import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateMember {
    private String firstName;
    private String lastName;
    private String birthDate;            // format: date
    private String gender;               // enum: MALE/FEMALE
    private String address;
    private String profession;
    private Integer phoneNumber;
    private String email;
    private String occupation;           // enum: JUNIOR, SENIOR, etc.
    private String collectivityIdentifier; // ID de la collectivité
    private List<String> referees;       // liste des IDs des référents
    private Boolean registrationFeePaid;
    private Boolean membershipDuesPaid;
}
