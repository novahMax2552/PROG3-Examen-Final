package org.prog3.prog3projetfinal.model;

import java.time.LocalDate;
import java.util.List;
import org.prog3.prog3projetfinal.model.enums.Gender;
import org.prog3.prog3projetfinal.model.enums.MemberOccupation;

public class Member {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private Gender gender;
    private String profession;
    private String phoneNumber;
    private String email;                    // Email
    private MemberOccupation occupation;
    private List<Member> referees;
    private String collectivityId;
    private LocalDate dateAdhesion;

    // Constructeurs
    public Member() {}

    public Member(String id, List<Member> referees, String collectivityId, LocalDate dateAdhesion) {
        this.id = id;
        this.referees = referees;
        this.collectivityId = collectivityId;
        this.dateAdhesion = dateAdhesion;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public MemberOccupation getOccupation() { return occupation; }
    public void setOccupation(MemberOccupation occupation) { this.occupation = occupation; }

    public List<Member> getReferees() { return referees; }
    public void setReferees(List<Member> referees) { this.referees = referees; }

    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

    public LocalDate getDateAdhesion() { return dateAdhesion; }
    public void setDateAdhesion(LocalDate dateAdhesion) { this.dateAdhesion = dateAdhesion; }
}
