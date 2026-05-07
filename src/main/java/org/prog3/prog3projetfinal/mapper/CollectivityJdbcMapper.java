package org.prog3.prog3projetfinal.mapper;

import org.prog3.prog3projetfinal.entity.Collectivity;
import org.prog3.prog3projetfinal.entity.CollectivityStructure;
import org.prog3.prog3projetfinal.entity.Member;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CollectivityJdbcMapper {

    public Collectivity mapFromResultSet(ResultSet rs) {
        try {
            return Collectivity.builder()
                    .id(rs.getString("id"))
                    .name(rs.getString("name"))
                    .number(rs.getInt("number"))
                    .location(rs.getString("location"))
                    .collectivityStructure(CollectivityStructure.builder()
                            .president(Member.builder().id(rs.getString("president_id")).build())
                            .vicePresident(Member.builder().id(rs.getString("vice_president_id")).build())
                            .treasurer(Member.builder().id(rs.getString("treasurer_id")).build())
                            .secretary(Member.builder().id(rs.getString("secretary_id")).build())
                            .build())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

