package org.prog3.prog3projetfinal.model;

import java.util.List;

public class Collectivity {

    private String id;
    private Integer number;
    private String name;
    private String location;
    private CollectivityStructure structure;
    private List<Member> members;

    public String getId() {
        return id;
    }
    public void setId(String id) {

        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {

        this.number = number;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {

        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {

        this.location = location;
    }

    public CollectivityStructure getStructure() {
        return structure;
    }
    public void setStructure(CollectivityStructure structure) {
        this.structure = structure;
    }

    public List<Member> getMembers() {
        return members;
    }
    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
