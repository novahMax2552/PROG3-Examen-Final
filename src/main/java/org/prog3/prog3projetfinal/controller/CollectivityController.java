package org.prog3.prog3projetfinal.controller;

import org.prog3.prog3projetfinal.controller.dto.*;
import org.prog3.prog3projetfinal.controller.dto.CreateCollectivityActivity;
import org.prog3.prog3projetfinal.controller.mapper.CollectivityDtoMapper;
import org.prog3.prog3projetfinal.controller.mapper.MembershipFeeDtoMapper;
import org.prog3.prog3projetfinal.entity.*;
import org.prog3.prog3projetfinal.entity.MembershipFee;
import org.prog3.prog3projetfinal.exception.BadRequestException;
import org.prog3.prog3projetfinal.exception.NotFoundException;
import org.prog3.prog3projetfinal.service.CollectivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class CollectivityController {
    private final CollectivityDtoMapper collectivityDtoMapper;
    private final MembershipFeeDtoMapper membershipFeeDtoMapper;
    private final CollectivityService collectivityService;

    @GetMapping("/collectivities/{id}")
    public ResponseEntity<?> getCollectivityById(@PathVariable String id) {
        try {
            Collectivity collectivity = collectivityService.getCollectivityById(id);
            return ResponseEntity.status(OK).body(collectivityDtoMapper.mapToDto(collectivity));
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/collectivities")
    public ResponseEntity<?> createCollectivity(@RequestBody List<CreateCollectivity> createCollectivities) {
        try {
            List<Collectivity> collectivities = createCollectivities.stream()
                    .map(collectivityDtoMapper::mapToEntity)
                    .toList();

            List<Collectivity> created = collectivityService.createCollectivities(collectivities);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(created.stream()
                            .map(collectivityDtoMapper::mapToDto)
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/collectivities/{id}/informations")
    public ResponseEntity<?> updateCollectivityInformation(@PathVariable String id,
                                                           @RequestBody CollectivityInformation collectivityInformation) {
        try {
            Collectivity updated = collectivityService.updateInformations(id,
                    collectivityInformation.getName(),
                    collectivityInformation.getNumber());
            return ResponseEntity.status(OK).body(collectivityDtoMapper.mapToDto(updated));
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> getCollectivityMembershipFeesByCollectivity(@PathVariable String id) {
        try {
            List<MembershipFee> fees = collectivityService.getMembershipFeesByCollectivityIdentifier(id);
            return ResponseEntity.status(OK)
                    .body(fees.stream()
                            .map(membershipFeeDtoMapper::mapToDto) // Entity → DTO
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> createCollectivityMembershipFee(
            @PathVariable String id,
            @RequestBody List<CreateMembershipFee> membershipFees) {
        try {
            List<MembershipFee> membershipFeesToCreate = membershipFees.stream()
                    .map(membershipFeeDtoMapper::mapToEntity) // DTO → Entity
                    .toList();

            List<MembershipFee> created = collectivityService.createMembershipFees(id, membershipFeesToCreate);

            return ResponseEntity.status(OK)
                    .body(created.stream()
                            .map(membershipFeeDtoMapper::mapToDto) // Entity → DTO
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/financialAccounts")
    public List<FinancialAccount> getFinancialAccounts(
            @PathVariable String id,
            @RequestParam String at) {
        return collectivityService.getFinancialAccounts(id, at);
    }

    @GetMapping("/{id}/transactions")
    public List<CollectivityTransaction> getTransactions(
            @PathVariable String id,
            @RequestParam String from,
            @RequestParam String to) {
        return collectivityService.getTransactions(id, from, to);
    }

    @GetMapping("/{id}/statistics")
    public List<CollectivityLocalStatistics> getLocalStatistics(
            @PathVariable String id,
            @RequestParam String from,
            @RequestParam String to) {
        return collectivityService.getLocalStatistics(id, from, to);
    }

    @GetMapping("/statistics")
    public List<CollectivityOverallStatistics> getOverallStatistics(
            @RequestParam String from,
            @RequestParam String to) {
        return collectivityService.getOverallStatistics(from, to);
    }

    @PostMapping("/{id}/activities")
    public List<CollectivityActivity> addActivities(
            @PathVariable String id,
            @RequestBody List<CreateCollectivityActivity> activities) {
        return collectivityService.addActivities(id, activities);
    }

    @GetMapping("/{id}/activities")
    public List<CollectivityActivity> getActivities(@PathVariable String id) {
        return collectivityService.getActivities(id);
    }

    @PostMapping("/{id}/activities/{activityId}/attendance")
    public List<ActivityMemberAttendance> confirmAttendance(
            @PathVariable String id,
            @PathVariable String activityId,
            @RequestBody List<CreateActivityMemberAttendance> attendanceList) {
        return collectivityService.confirmAttendance(id, activityId, attendanceList);
    }

    @GetMapping("/{id}/activities/{activityId}/attendance")
    public List<ActivityMemberAttendance> getAttendance(
            @PathVariable String id,
            @PathVariable String activityId) {
        return collectivityService.getAttendance(id, activityId);
    }
}
