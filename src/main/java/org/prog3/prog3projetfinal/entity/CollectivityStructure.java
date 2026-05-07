package org.prog3.prog3projetfinal.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CollectivityStructure {
    private Member president;       // entity.Member
    private Member vicePresident;   // entity.Member
    private Member treasurer;       // entity.Member
    private Member secretary;       // entity.Member
}
