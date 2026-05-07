package org.prog3.prog3projetfinal.service;

import lombok.RequiredArgsConstructor;
import org.prog3.prog3projetfinal.controller.dto.CreateCollectivityActivity;
import org.prog3.prog3projetfinal.entity.Member;
import org.prog3.prog3projetfinal.entity.MembershipFee;
import org.springframework.stereotype.Service;
import org.prog3.prog3projetfinal.controller.dto.*;
import org.prog3.prog3projetfinal.entity.*;
import org.prog3.prog3projetfinal.exception.BadRequestException;
import org.prog3.prog3projetfinal.exception.NotFoundException;
import org.prog3.prog3projetfinal.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.prog3.prog3projetfinal.entity.ActivityStatus.ACTIVE;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class CollectivityService {
    private final CollectivityRepository collectivityRepository;
    private final MembershipFeeRepository membershipFeeRepository;
    private final PaymentRepository paymentRepository;
    private final AttendanceRepository attendanceRepository;
    private final FinancialAccountRepository financialAccountRepository;
    private final TransactionRepository transactionRepository;
    private final ActivityRepository activityRepository;

    public List<Collectivity> createCollectivities(List<Collectivity> collectivities) {
        for (Collectivity collectivity : collectivities) {
            if (!collectivity.hasEnoughMembers()) {
                throw new BadRequestException("Collectivity must have at least 10 members, actual is "
                        + (collectivity.getMembers() != null ? collectivity.getMembers().size() : 0));
            }
            collectivity.setId(randomUUID().toString());
        }
        return collectivityRepository.saveAll(collectivities);
    }

    public Collectivity getCollectivityById(String id) {
        return collectivityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Collectivity.id=" + id + " not found"));
    }

    public Collectivity updateInformations(String collectivityId, String actualName, Integer actualNumber) {
        Collectivity collectivity = collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException("Collectivity.id=" + collectivityId + " not found"));

        if (actualNumber != null && collectivityRepository.isNumberExists(actualNumber)) {
            throw new BadRequestException("Collectivity.number=" + actualNumber + " already exists");
        }
        if (actualName != null && collectivityRepository.isNameExists(actualName)) {
            throw new BadRequestException("Collectivity.name=" + actualName + " already exists");
        }

        collectivity.setName(actualName);
        collectivity.setNumber(actualNumber);

        return collectivityRepository.saveAll(List.of(collectivity)).get(0);
    }

    // ✅ Cotisations
    public List<MembershipFee> getMembershipFeesByCollectivityIdentifier(String collectivityIdentifier) {
        Collectivity collectivity = collectivityRepository.findById(collectivityIdentifier)
                .orElseThrow(() -> new NotFoundException("Collectivity.id=" + collectivityIdentifier + " not found"));
        return membershipFeeRepository.getMembershipFeesByCollectivityId(collectivity.getId());
    }

    public List<MembershipFee> createMembershipFees(String collectivityIdentifier, List<MembershipFee> membershipFees) {
        Collectivity collectivity = collectivityRepository.findById(collectivityIdentifier)
                .orElseThrow(() -> new NotFoundException("Collectivity.id=" + collectivityIdentifier + " not found"));

        for (MembershipFee membershipFee : membershipFees) {
            membershipFee.setId(randomUUID().toString());
            membershipFee.setStatus(ACTIVE);
            membershipFee.setCollectivityOwner(collectivity);
        }
        return membershipFeeRepository.saveAll(membershipFees);
    }

    // ✅ Comptes financiers
    public List<FinancialAccount> getFinancialAccounts(String id, String at) {
        Collectivity collectivity = collectivityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Collectivity.id=" + id + " not found"));
        LocalDate date = LocalDate.parse(at);
        return financialAccountRepository.findByCollectivityAndDate(collectivity.getId(), date);
    }

    // ✅ Transactions
    public List<CollectivityTransaction> getTransactions(String id, String from, String to) {
        Collectivity collectivity = collectivityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Collectivity.id=" + id + " not found"));
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        return transactionRepository.findByCollectivityAndPeriod(collectivity.getId(), fromDate, toDate);
    }

    // ✅ Statistiques locales
    public List<CollectivityLocalStatistics> getLocalStatistics(String collectivityId, String from, String to) {
        Collectivity collectivity = collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException("Collectivity.id=" + collectivityId + " not found"));

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        List<Member> members = collectivity.getMembers();
        List<MembershipFee> activeFees = membershipFeeRepository.findActiveByCollectivityId(collectivityId);

        List<CollectivityLocalStatistics> statistics = new ArrayList<>();

        for (Member member : members) {
            double earnedAmount = paymentRepository.findByMemberAndPeriod(member.getId(), fromDate, toDate)
                    .stream()
                    .mapToDouble(MemberPayment::getAmount)
                    .sum();

            double unpaidAmount = activeFees.stream()
                    .mapToDouble(MembershipFee::getAmount)
                    .sum() - earnedAmount;
            if (unpaidAmount < 0) unpaidAmount = 0;

            double assiduityPercentage = attendanceRepository.calculateAssiduity(member.getId(), fromDate, toDate);

            CollectivityLocalStatistics stat = new CollectivityLocalStatistics();
            stat.setMemberDescription(new MemberDescription(member.getId(), member.getFirstName(), member.getLastName(), member.getEmail(), member.getOccupation().name()));
            stat.setEarnedAmount(earnedAmount);
            stat.setUnpaidAmount(unpaidAmount);
            stat.setAssiduityPercentage(assiduityPercentage);

            statistics.add(stat);
        }

        return statistics;
    }

    public List<CollectivityOverallStatistics> getOverallStatistics(String from, String to) {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        List<Collectivity> collectivities = collectivityRepository.findAll();
        List<CollectivityOverallStatistics> statistics = new ArrayList<>();

        for (Collectivity collectivity : collectivities) {
            List<Member> members = collectivity.getMembers();

            long totalMembers = members.size();
            long membersCurrentDue = members.stream()
                    .filter(member -> paymentRepository.isMemberUpToDate(member.getId(), fromDate, toDate))
                    .count();
            double overallMemberCurrentDuePercentage = totalMembers > 0
                    ? (membersCurrentDue * 100.0 / totalMembers)
                    : 0.0;

            long newMembersNumber = members.stream()
                    .filter(member -> member.getDateAdhesion() != null
                            && !member.getDateAdhesion().isBefore(fromDate)
                            && !member.getDateAdhesion().isAfter(toDate))
                    .count();

            double overallMemberAssiduityPercentage = attendanceRepository.calculateGlobalAssiduity(collectivity.getId(), fromDate, toDate);

            CollectivityOverallStatistics stat = new CollectivityOverallStatistics();
            stat.setCollectivityInformation(new CollectivityInformation(collectivity.getName(), collectivity.getNumber()));
            stat.setNewMembersNumber((int) newMembersNumber);
            stat.setOverallMemberCurrentDuePercentage(overallMemberCurrentDuePercentage);
            stat.setOverallMemberAssiduityPercentage(overallMemberAssiduityPercentage);

            statistics.add(stat);
        }

        return statistics;
    }

    public List<CollectivityActivity> addActivities(String id, List<CreateCollectivityActivity> activities) {
        Collectivity collectivity = collectivityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Collectivity.id=" + id + " not found"));
        return activityRepository.saveAll(id, activities);
    }

    public List<CollectivityActivity> getActivities(String id) {
        return activityRepository.findByCollectivityId(id);
    }

    public List<ActivityMemberAttendance> confirmAttendance(String id, String activityId, List<CreateActivityMemberAttendance> attendanceList) {
        Collectivity collectivity = collectivityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Collectivity.id=" + id + " not found"));
        return attendanceRepository.saveAttendance(activityId, attendanceList);
    }

    public List<ActivityMemberAttendance> getAttendance(String id, String activityId) {
        return attendanceRepository.findByActivityId(activityId);
    }
}
