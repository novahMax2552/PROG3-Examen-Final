package org.prog3.prog3projetfinal.controller.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateCollectivityStructure {
    private String president;       // MemberIdentifier (ID)
    private String vicePresident;   // MemberIdentifier (ID)
    private String treasurer;       // MemberIdentifier (ID)
    private String secretary;       // MemberIdentifier (ID)
}
