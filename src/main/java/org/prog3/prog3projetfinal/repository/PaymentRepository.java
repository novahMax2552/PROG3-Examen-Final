package org.prog3.prog3projetfinal.repository;

import org.prog3.prog3projetfinal.entity.MemberPayment;
import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository {
    List<MemberPayment> findByMemberAndPeriod(String memberId, LocalDate from, LocalDate to);
    boolean isMemberUpToDate(String memberId, LocalDate from, LocalDate to);
}
