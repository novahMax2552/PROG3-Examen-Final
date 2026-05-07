package org.prog3.prog3projetfinal.controller.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CollectivityStructureDto {
    private Member president;       // DTO Member
    private Member vicePresident;   // DTO Member
    private Member treasurer;       // DTO Member
    private Member secretary;       // DTO Member
}
