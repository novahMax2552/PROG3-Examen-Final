package org.prog3.prog3projetfinal.mapper;

import org.prog3.prog3projetfinal.entity.Member;
import org.prog3.prog3projetfinal.entity.MemberOccupation;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MemberMapper {

    public Member mapFromResultSet(ResultSet rs) {
        try {
            return Member.builder()
                    .id(rs.getString("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .email(rs.getString("email"))
                    .occupation(rs.getString("occupation") == null ? null :
                            MemberOccupation.valueOf(rs.getString("occupation")))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping ResultSet to Member", e);
        }
    }
}
