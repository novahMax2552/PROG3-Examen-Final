package org.prog3.prog3projetfinal.mapper;

import org.prog3.prog3projetfinal.controller.dto.CollectivityDto;
import org.prog3.prog3projetfinal.controller.dto.CollectivityStructureDto;
import org.prog3.prog3projetfinal.controller.mapper.MemberDtoMapper;
import org.prog3.prog3projetfinal.entity.Collectivity;
import org.prog3.prog3projetfinal.entity.CollectivityStructure;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectivityMapper {

    private final MemberDtoMapper memberDtoMapper;

    public CollectivityMapper(MemberDtoMapper memberDtoMapper) {
        this.memberDtoMapper = memberDtoMapper;
    }

    public CollectivityDto toDto(Collectivity entity) {
        if (entity == null) return null;
        return CollectivityDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .number(entity.getNumber())
                .location(entity.getLocation())
                .federationApproval(entity.getFederationApproval())
                .collectivityStructure(toStructureDto(entity.getCollectivityStructure()))
                .members(entity.getMembers() != null
                        ? entity.getMembers().stream()
                          .map(memberDtoMapper::mapToDto) // entity.Member → dto.Member
                          .toList()
                        : List.of())
                .build();
    }

    public Collectivity toEntity(CollectivityDto dto) {
        if (dto == null) return null;
        return Collectivity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .number(dto.getNumber())
                .location(dto.getLocation())
                .federationApproval(dto.getFederationApproval())
                .collectivityStructure(toStructureEntity(dto.getCollectivityStructure()))
                .members(dto.getMembers() != null
                        ? dto.getMembers().stream()
                          .map(memberDtoMapper::mapToEntity) // dto.Member → entity.Member
                          .toList()
                        : List.of())
                .build();
    }

    private CollectivityStructureDto toStructureDto(CollectivityStructure entity) {
        if (entity == null) return null;
        return CollectivityStructureDto.builder()
                .president(memberDtoMapper.mapToDto(entity.getPresident()))
                .vicePresident(memberDtoMapper.mapToDto(entity.getVicePresident()))
                .treasurer(memberDtoMapper.mapToDto(entity.getTreasurer()))
                .secretary(memberDtoMapper.mapToDto(entity.getSecretary()))
                .build();
    }

    private CollectivityStructure toStructureEntity(CollectivityStructureDto dto) {
        if (dto == null) return null;
        return CollectivityStructure.builder()
                .president(memberDtoMapper.mapToEntity(dto.getPresident()))
                .vicePresident(memberDtoMapper.mapToEntity(dto.getVicePresident()))
                .treasurer(memberDtoMapper.mapToEntity(dto.getTreasurer()))
                .secretary(memberDtoMapper.mapToEntity(dto.getSecretary()))
                .build();
    }
}
