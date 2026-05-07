package org.prog3.prog3projetfinal.service;

import org.prog3.prog3projetfinal.entity.Member;
import org.prog3.prog3projetfinal.exception.BadRequestException;
import org.prog3.prog3projetfinal.exception.NotFoundException;
import org.prog3.prog3projetfinal.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> addNewMembers(List<Member> memberList) {
        for (Member member : memberList) {
            if (!member.refereesAreEligible()) {
                throw new BadRequestException("Member.id=" + member.getId() + " member referees are not eligible");
            }
            if (!member.getMembershipDuesPaid()) {
                throw new BadRequestException("Member.id=" + member.getId() + " membership dues not paid");
            }
            if (!member.getRegistrationFeePaid()) {
                throw new BadRequestException("Member.id=" + member.getId() + " membership fees not paid");
            }
            member.setId(randomUUID().toString());
        }
        return memberRepository.saveAll(memberList);
    }

    public Member getMemberById(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Member.id=" + id + " not found"));
    }

    public List<Member> createMembers(List<Member> members) {
        return memberRepository.saveAll(members);
    }
}
