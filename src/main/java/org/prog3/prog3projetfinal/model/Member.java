package org.prog3.prog3projetfinal.model;

import java.time.LocalDate;
import java.util.List;

import org.prog3.prog3projetfinal.model.enums.MemberOccupation;
import org.prog3.prog3projetfinal.model.enums.Gender;

public class Member extends MemberInformation {
    private String id;
    private List<Member> referees;
    private String collectivityId;
    private LocalDate dateAdhesion;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public List<Member> getReferees() {
        return referees;
    }
    public void setReferees(List<Member> referees) {
        this.referees = referees;
    }

    public String getCollectivityId() {
        return collectivityId;
    }
    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }

    public LocalDate getDateAdhesion() {
        return dateAdhesion;
    }
    public void setDateAdhesion(LocalDate dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }
}

