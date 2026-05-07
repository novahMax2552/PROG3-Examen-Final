package org.prog3.prog3projetfinal.controller;

import org.prog3.prog3projetfinal.model.CollectivityLocalStatistics;
import org.prog3.prog3projetfinal.model.CollectivityOverallStatistics;
import org.prog3.prog3projetfinal.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivites")
public class StatisticsController {
    private final StatisticsService service;

    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<List<CollectivityLocalStatistics>> getLocalStats(
            @PathVariable String id,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        return ResponseEntity.ok(service.getLocalStatistics(id, from, to));
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<CollectivityOverallStatistics>> getOverallStats(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        return ResponseEntity.ok(service.getOverallStatistics(from, to));
    }
}

