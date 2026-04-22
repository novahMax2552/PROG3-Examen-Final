package org.prog3.prog3projetfinal.service;

import org.prog3.prog3projetfinal.Dao.MembershipFeeDao;
import org.prog3.prog3projetfinal.model.MembershipFee;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class MembershipFeeService {

    private final MembershipFeeDao dao;

    public MembershipFeeService(MembershipFeeDao dao) {
        this.dao = dao;
    }

    public List<MembershipFee> getFeesByCollectivity(UUID collectivityId) throws SQLException {
        return dao.findByCollectivityId(collectivityId);
    }

    public List<MembershipFee> createFees(UUID collectivityId, List<MembershipFee> fees) throws SQLException {
        for (MembershipFee fee : fees) {
            if (fee.getAmount() <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }
        }
        return dao.createFees(collectivityId, fees);
    }
}
