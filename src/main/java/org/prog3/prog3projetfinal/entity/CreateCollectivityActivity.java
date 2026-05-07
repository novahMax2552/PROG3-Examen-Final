package org.prog3.prog3projetfinal.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateCollectivityActivity {
    private String label;
    private String activityType;
    private String executiveDate;
}

