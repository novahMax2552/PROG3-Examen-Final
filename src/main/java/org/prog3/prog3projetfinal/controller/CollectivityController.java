package org.prog3.prog3projetfinal.controller;

import org.prog3.prog3projetfinal.model.AssignCollectivityIdentity;
import org.prog3.prog3projetfinal.model.Collectivity;
import org.prog3.prog3projetfinal.model.CreateCollectivity;
import org.prog3.prog3projetfinal.service.CollectivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {
    private final CollectivityService service;

    public CollectivityController(CollectivityService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<List<Collectivity>> createCollectivities(
            @RequestBody List<CreateCollectivity> collectivities) {
        try {
            List<Collectivity> created = service.createCollectivities(collectivities);
            return ResponseEntity.status(201).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/identity")
    public ResponseEntity<Collectivity> assignIdentity(
            @PathVariable String id,
            @RequestBody AssignCollectivityIdentity identity) {
        try {
            Collectivity updated = service.assignIdentity(id, identity);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

