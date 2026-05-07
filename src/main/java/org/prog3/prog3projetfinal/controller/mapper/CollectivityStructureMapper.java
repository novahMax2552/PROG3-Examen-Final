package org.prog3.prog3projetfinal.controller.mapper;

import org.prog3.prog3projetfinal.controller.dto.CollectivityStructureDto;
import org.prog3.prog3projetfinal.entity.CollectivityStructure;
import org.springframework.stereotype.Component;

@Component
public class    CollectivityStructureMapper {

    private final MemberDtoMapper memberDtoMapper;

    public CollectivityStructureMapper(MemberDtoMapper memberDtoMapper) {
        this.memberDtoMapper = memberDtoMapper;
    }

    public CollectivityStructureDto toDto(CollectivityStructure entity) {
        if (entity == null) return null;
        return CollectivityStructureDto.builder()
                .president(memberDtoMapper.mapToDto(entity.getPresident()))
                .vicePresident(memberDtoMapper.mapToDto(entity.getVicePresident()))
                .treasurer(memberDtoMapper.mapToDto(entity.getTreasurer()))
                .secretary(memberDtoMapper.mapToDto(entity.getSecretary()))
                .build();
    }

    public CollectivityStructure toEntity(CollectivityStructureDto dto) {
        if (dto == null) return null;
        return CollectivityStructure.builder()
                .president(memberDtoMapper.mapToEntity(dto.getPresident()))
                .vicePresident(memberDtoMapper.mapToEntity(dto.getVicePresident()))
                .treasurer(memberDtoMapper.mapToEntity(dto.getTreasurer()))
                .secretary(memberDtoMapper.mapToEntity(dto.getSecretary()))
                .build();
    }
}

