package org.prog3.prog3projetfinal.controller.mapper;

import org.prog3.prog3projetfinal.controller.dto.CreateCollectivity;
import org.prog3.prog3projetfinal.controller.dto.CreateCollectivityStructure;
import org.prog3.prog3projetfinal.controller.dto.CollectivityDto;
import org.prog3.prog3projetfinal.controller.dto.CollectivityStructureDto;
import org.prog3.prog3projetfinal.entity.Collectivity;
import org.prog3.prog3projetfinal.entity.CollectivityStructure;
import org.prog3.prog3projetfinal.entity.Member;
import org.prog3.prog3projetfinal.exception.NotFoundException;
import org.prog3.prog3projetfinal.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CollectivityDtoMapper {
    private final MemberRepository memberRepository;
    private final MemberDtoMapper memberDtoMapper;

    // ✅ Entity → DTO
    public CollectivityDto mapToDto(Collectivity entity) {
        CollectivityStructure structure = entity.getCollectivityStructure();
        return CollectivityDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .number(entity.getNumber())
                .location(entity.getLocation())
                .federationApproval(entity.getFederationApproval())
                .collectivityStructure(structure == null ? null : CollectivityStructureDto.builder()
                                                                  .president(memberDtoMapper.mapToDto(structure.getPresident()))
                                                                  .vicePresident(memberDtoMapper.mapToDto(structure.getVicePresident()))
                                                                  .treasurer(memberDtoMapper.mapToDto(structure.getTreasurer()))
                                                                  .secretary(memberDtoMapper.mapToDto(structure.getSecretary()))
                                                                  .build())
                .members(entity.getMembers() != null
                        ? entity.getMembers().stream()
                          .map(memberDtoMapper::mapToDto) // entity.Member → dto.Member
                          .toList()
                        : List.of())
                .build();
    }

    // ✅ DTO → Entity (structure)
    public CollectivityStructure mapToEntity(CreateCollectivityStructure dto) {
        if (dto == null) return null;
        return CollectivityStructure.builder()
                .president(memberRepository.findById(dto.getPresident())
                        .orElseThrow(() -> new NotFoundException("Member.id=" + dto.getPresident() + " not found")))
                .vicePresident(memberRepository.findById(dto.getVicePresident())
                        .orElseThrow(() -> new NotFoundException("Member.id=" + dto.getVicePresident() + " not found")))
                .treasurer(memberRepository.findById(dto.getTreasurer())
                        .orElseThrow(() -> new NotFoundException("Member.id=" + dto.getTreasurer() + " not found")))
                .secretary(memberRepository.findById(dto.getSecretary())
                        .orElseThrow(() -> new NotFoundException("Member.id=" + dto.getSecretary() + " not found")))
                .build();
    }

    // ✅ DTO → Entity (collectivity)
    public Collectivity mapToEntity(CreateCollectivity dto) {
        if (dto == null) return null;
        Collectivity entity = new Collectivity();
        entity.setLocation(dto.getLocation());
        entity.setFederationApproval(dto.getFederationApproval());
        entity.setCollectivityStructure(mapToEntity(dto.getStructure()));

        List<Member> members = dto.getMembers() != null
                ? dto.getMembers().stream()
                  .map(memberIdentifier -> memberRepository.findById(memberIdentifier)
                                           .orElseThrow(() -> new NotFoundException("Member.id=" + memberIdentifier + " not found")))
                  .toList()
                : List.of();
        entity.setMembers(members);

        return entity;
    }
}
