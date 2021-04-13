package com.gis.test.bookcinema.db.repo;

import com.gis.test.bookcinema.db.model.Cinema;
import com.gis.test.bookcinema.db.model.Hall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

@Repository
public class HallRepo {
    private static final String SELECT_HALL = "SELECT * FROM HALL WHERE REFCINEMA=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Hall> findByCinema(Integer cinemdId) {
        return jdbcTemplate.query(SELECT_HALL, new Object[]{cinemdId}, new int[]{Types.INTEGER}, (rs, rowNum) ->
                (new Hall(
                        rs.getInt("id"),
                        rs.getString("NAME")
                )));
    }

}
