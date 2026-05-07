package org.prog3.prog3projetfinal.controller.mapper;

import org.prog3.prog3projetfinal.controller.dto.CreateMembershipFee;
import org.prog3.prog3projetfinal.controller.dto.MembershipFee; // DTO
import org.prog3.prog3projetfinal.controller.dto.Frequency;
import org.prog3.prog3projetfinal.controller.dto.ActivityStatus;
import org.springframework.stereotype.Component;

@Component
public class MembershipFeeDtoMapper {

    public MembershipFee mapToDto(org.prog3.prog3projetfinal.entity.MembershipFee entity) {
        if (entity == null) return null;
        return MembershipFee.builder()
                .id(entity.getId())
                .label(entity.getLabel())
                .amount(entity.getAmount())
                .frequency(entity.getFrequency() == null ? null :
                        Frequency.valueOf(entity.getFrequency().name()))
                .status(entity.getStatus() == null ? null :
                        ActivityStatus.valueOf(entity.getStatus().name()))
                .eligibleFrom(entity.getEligibleFrom())
                .build();
    }

    public org.prog3.prog3projetfinal.entity.MembershipFee mapToEntity(CreateMembershipFee dto) {
        org.prog3.prog3projetfinal.entity.MembershipFee entity =
                new org.prog3.prog3projetfinal.entity.MembershipFee();
        entity.setLabel(dto.getLabel());
        entity.setAmount(dto.getAmount());
        entity.setFrequency(dto.getFrequency() == null ? null :
                org.prog3.prog3projetfinal.entity.Frequency.valueOf(dto.getFrequency().name()));
        entity.setEligibleFrom(dto.getEligibleFrom());
        return entity;
    }
}
