package org.prog3.prog3projetfinal.controller.dto;

import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateCollectivity {
    private String location;                         // localisation
    private List<String> members;                    // liste des IDs des membres
    private Boolean federationApproval;              // validation fédération
    private CreateCollectivityStructure structure;   // structure avec IDs
}
