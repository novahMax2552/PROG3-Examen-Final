package org.prog3.prog3projetfinal.entity;

import lombok.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CollectivityActivity {
    private String id;
    private String collectivityId;
    private String name;
    private String description;
    private String activityType;
    private LocalDate activityDate;
}
