package org.prog3.prog3projetfinal.repository;

import org.prog3.prog3projetfinal.entity.CollectivityActivity;
import org.prog3.prog3projetfinal.controller.dto.CreateCollectivityActivity;

import java.time.LocalDate;
import java.util.List;

public interface ActivityRepository {
    List<CollectivityActivity> findByCollectivityAndPeriod(String collectivityId, LocalDate from, LocalDate to);

    List<CollectivityActivity> findByCollectivityId(String collectivityId);

    List<CollectivityActivity> saveAll(String collectivityId, List<CreateCollectivityActivity> activities);
}
