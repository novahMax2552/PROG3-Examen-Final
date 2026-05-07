package org.prog3.prog3projetfinal.entity;

import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Collectivity {
    private String id;
    private String name;
    private Integer number;
    private String location;
    private Boolean federationApproval;
    private CollectivityStructure collectivityStructure;
    private List<Member> members;

    public boolean hasEnoughMembers() {
        return members != null && members.size() >= 10;
    }
}
