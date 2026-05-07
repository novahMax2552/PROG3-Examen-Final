package org.prog3.prog3projetfinal.controller.dto;

import lombok.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateCollectivityActivity {
    private String id;
    private String name;
    private String description;
    private String activityType;
    private LocalDate activityDate;
}
