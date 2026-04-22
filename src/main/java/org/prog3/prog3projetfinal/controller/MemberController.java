package org.prog3.prog3projetfinal.controller;

import org.prog3.prog3projetfinal.model.CreateMember;
import org.prog3.prog3projetfinal.model.Member;
import org.prog3.prog3projetfinal.service.MemberService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<List<Member>> create(@RequestBody List<CreateMember> members) {
        try {
            List<Member> created = service.createMembers(members);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


}
