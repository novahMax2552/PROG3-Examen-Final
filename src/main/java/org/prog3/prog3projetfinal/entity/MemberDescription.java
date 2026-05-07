package org.prog3.prog3projetfinal.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberDescription {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String occupation;
}

