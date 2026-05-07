package org.prog3.prog3projetfinal.controller.dto;

import lombok.*;
import org.prog3.prog3projetfinal.entity.MemberDescription;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CollectivityLocalStatistics {
    private MemberDescription memberDescription;
    private double earnedAmount;
    private double unpaidAmount;
    private double assiduityPercentage;

    public void setMemberDescription(MemberDescription memberDescription) {
    }
}

