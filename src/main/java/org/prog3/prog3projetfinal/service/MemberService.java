package org.prog3.prog3projetfinal.service;

import org.prog3.prog3projetfinal.Dao.MemberDao;
import org.prog3.prog3projetfinal.model.CreateMember;
import org.prog3.prog3projetfinal.model.Member;
import org.prog3.prog3projetfinal.model.enums.MemberOccupation;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberDao dao;

    public MemberService(MemberDao dao) {
        this.dao = dao;
    }

    public List<Member> createMembers(List<CreateMember> members) {
        List<Member> created = new ArrayList<>();

        for (CreateMember m : members) {
            if (!m.isRegistrationFeePaid() || !m.isMembershipDuesPaid()) {
                throw new IllegalArgumentException("Registration fee or membership dues not paid");
            }
            if (m.getReferees() == null || m.getReferees().size() < 2) {
                throw new IllegalArgumentException("At least two referees required");
            }

            try {
                List<Member> referees = dao.findMembersByIds(m.getReferees());

                long internalCount = referees.stream()
                        .filter(r -> r.getOccupation() == MemberOccupation.SENIOR
                                && r.getCollectivityId().equals(m.getCollectivityIdentifier())
                                && r.getDateAdhesion().isBefore(LocalDate.now().minusDays(90)))
                        .count();

                if (internalCount < (referees.size() - internalCount)) {
                    throw new IllegalArgumentException("Not enough internal referees");
                }

                Member newMember = dao.save(m, referees);
                created.add(newMember);

            } catch (SQLException e) {
                throw new RuntimeException("Database error while saving member", e);
            }
        }

        return created;
    }
    public Optional<Member> findById(String id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching member", e);
        }
    }
}
