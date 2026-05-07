package org.prog3.prog3projetfinal.model;

public class CollectivityMapper {

    public static CollectivityStructure toCollectivityStructure(CreateCollectivityStructure input) {
        CollectivityStructure structure = new CollectivityStructure();

        Member president = new Member();
        president.setId(input.getPresident());

        Member vicePresident = new Member();
        vicePresident.setId(input.getVicePresident());

        Member treasurer = new Member();
        treasurer.setId(input.getTreasurer());

        Member secretary = new Member();
        secretary.setId(input.getSecretary());

        structure.setPresident(president);
        structure.setVicePresident(vicePresident);
        structure.setTreasurer(treasurer);
        structure.setSecretary(secretary);

        return structure;
    }
}

