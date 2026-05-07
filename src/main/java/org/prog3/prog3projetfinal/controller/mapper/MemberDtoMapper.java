package org.prog3.prog3projetfinal.controller.mapper;

import org.prog3.prog3projetfinal.controller.dto.CreateMember;
import org.prog3.prog3projetfinal.controller.dto.Member;
import org.prog3.prog3projetfinal.entity.MemberOccupation;
import org.springframework.stereotype.Component;

@Component
public class MemberDtoMapper {

    // ✅ Entité → DTO
    public Member mapToDto(org.prog3.prog3projetfinal.entity.Member entity) {
        if (entity == null) return null;
        return Member.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .gender(entity.getGender() != null ? entity.getGender().name() : null)
                .address(entity.getAddress())
                .profession(entity.getProfession())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .occupation(entity.getOccupation() != null ? entity.getOccupation().name() : null) // enum → String
                .referees(entity.getReferees() != null
                        ? entity.getReferees().stream()
                          .map(this::mapToDto)
                          .toList()
                        : java.util.List.of())
                .build();
    }

    // ✅ DTO CreateMember → Entité
    public org.prog3.prog3projetfinal.entity.Member mapToEntity(CreateMember dto) {
        if (dto == null) return null;
        return org.prog3.prog3projetfinal.entity.Member.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender() != null ? org.prog3.prog3projetfinal.entity.Gender.valueOf(dto.getGender()) : null)
                .address(dto.getAddress())
                .profession(dto.getProfession())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .occupation(dto.getOccupation() != null ? MemberOccupation.valueOf(dto.getOccupation()) : null) // String → enum
                .build();
    }

    // ✅ DTO Member → Entité
    public org.prog3.prog3projetfinal.entity.Member mapToEntity(Member dto) {
        if (dto == null) return null;
        return org.prog3.prog3projetfinal.entity.Member.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender() != null ? org.prog3.prog3projetfinal.entity.Gender.valueOf(dto.getGender()) : null)
                .address(dto.getAddress())
                .profession(dto.getProfession())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .occupation(dto.getOccupation() != null ? MemberOccupation.valueOf(dto.getOccupation()) : null) // String → enum
                .build();
    }
}
