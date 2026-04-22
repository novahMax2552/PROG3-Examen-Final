package org.prog3.prog3projetfinal.controller;

import org.prog3.prog3projetfinal.model.MembershipFee;
import org.prog3.prog3projetfinal.service.MembershipFeeService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/collectivities/{id}/membershipFees")
public class MembershipFeeController {

    private final MembershipFeeService service;

    public MembershipFeeController(MembershipFeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<MembershipFee> getFees(@PathVariable("id") UUID collectivityId) throws SQLException {
        return service.getFeesByCollectivity(collectivityId);
    }

    @PostMapping
    public List<MembershipFee> createFees(@PathVariable("id") UUID collectivityId,
                                          @RequestBody List<MembershipFee> fees) throws SQLException {
        return service.createFees(collectivityId, fees);
    }
}

