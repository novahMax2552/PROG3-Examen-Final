package org.prog3.prog3projetfinal.controller.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CollectivityOverallStatistics {
    private CollectivityInformation collectivityInformation;
    private int newMembersNumber;
    private double overallMemberCurrentDuePercentage;
    private double overallMemberAssiduityPercentage;
}
