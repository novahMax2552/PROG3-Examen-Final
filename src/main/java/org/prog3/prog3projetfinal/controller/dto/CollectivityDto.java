package org.prog3.prog3projetfinal.controller.dto;

import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CollectivityDto {
    private String id;                              // identifiant unique
    private String name;                            // nom
    private Integer number;                         // numéro
    private String location;                        // localisation
    private Boolean federationApproval;             // validation fédération
    private CollectivityStructureDto collectivityStructure; // structure avec membres complets
    private List<Member> members;                   // liste des membres complets
}
