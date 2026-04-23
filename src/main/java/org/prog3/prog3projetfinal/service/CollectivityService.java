package org.prog3.prog3projetfinal.service;

import org.prog3.prog3projetfinal.Dao.CollectivityDao;
import org.prog3.prog3projetfinal.model.AssignCollectivityIdentity;
import org.prog3.prog3projetfinal.model.Collectivity;
import org.prog3.prog3projetfinal.model.CreateCollectivity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CollectivityService {
    private final CollectivityDao dao;

    public CollectivityService(CollectivityDao dao) {
        this.dao = dao;
    }

    public List<Collectivity> createCollectivities(List<CreateCollectivity> requests) {
        for (CreateCollectivity req : requests) {
            if (!req.isFederationApproval() || req.getStructure() == null) {
                throw new IllegalArgumentException("Federation approval missing or structure incomplete");
            }
            if (!dao.membersExist(req.getMembers())) {
                throw new NoSuchElementException("One or more members not found");
            }
        }
        return dao.insertCollectivities(requests);
    }

    public Collectivity assignIdentity(String id, AssignCollectivityIdentity identity) {
        Collectivity c = dao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Collectivity not found"));

        if (c.getNumber() != null || c.getName() != null) {
            throw new IllegalArgumentException("Identity already assigned");
        }

        if (dao.existsByNumber(identity.getNumber())) {
            throw new IllegalArgumentException("Number already exists");
        }
        if (dao.existsByName(identity.getName())) {
            throw new IllegalArgumentException("Name already exists");
        }

        return dao.updateIdentity(id, identity.getNumber(), identity.getName());
    }
}

