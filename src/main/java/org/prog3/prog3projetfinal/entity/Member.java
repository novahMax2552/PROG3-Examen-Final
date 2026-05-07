package org.prog3.prog3projetfinal.entity;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private Integer phoneNumber;
    private String email;
    private MemberOccupation occupation;
    private List<Member> referees;
    private Boolean registrationFeePaid;
    private Boolean membershipDuesPaid;
    private LocalDate dateAdhesion;

    public boolean refereesAreEligible() {
        if (referees == null || referees.size() < 2) {
            return false;
        }

        long internalCount = referees.stream()
                .filter(r -> r.getOccupation() == MemberOccupation.SENIOR
                        && r.getDateAdhesion() != null
                        && r.getDateAdhesion().isBefore(LocalDate.now().minusDays(90)))
                .count();

        long externalCount = referees.size() - internalCount;

        return internalCount >= externalCount;
    }
}
