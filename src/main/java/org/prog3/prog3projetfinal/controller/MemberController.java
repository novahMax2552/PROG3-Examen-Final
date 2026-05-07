package org.prog3.prog3projetfinal.controller;

import org.prog3.prog3projetfinal.controller.dto.CreateMember;
import org.prog3.prog3projetfinal.controller.dto.Member; // DTO
import org.prog3.prog3projetfinal.controller.mapper.MemberDtoMapper;
import org.prog3.prog3projetfinal.exception.BadRequestException;
import org.prog3.prog3projetfinal.exception.NotFoundException;
import org.prog3.prog3projetfinal.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberDtoMapper memberDtoMapper;
    private final MemberService memberService;

    @GetMapping("/members/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable String id) {
        try {
            org.prog3.prog3projetfinal.entity.Member entity = memberService.getMemberById(id);
            // Mapper convertit en DTO
            return ResponseEntity.status(OK).body(memberDtoMapper.mapToDto(entity));
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/members")
    public ResponseEntity<?> createMembers(@RequestBody List<CreateMember> createMembers) {
        try {
            List<org.prog3.prog3projetfinal.entity.Member> entities = createMembers.stream()
                    .map(memberDtoMapper::mapToEntity)
                    .toList();

            // Service travaille avec des entités
            List<org.prog3.prog3projetfinal.entity.Member> created = memberService.createMembers(entities);

            // Entity → DTO pour la réponse
            List<Member> dtos = created.stream()
                    .map(memberDtoMapper::mapToDto)
                    .toList();

            return ResponseEntity.status(HttpStatus.OK).body(dtos);
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
