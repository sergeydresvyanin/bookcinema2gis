package com.gis.test.bookcinema.db.repo;

import com.gis.test.bookcinema.db.model.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CinemaRepo {

    private static final String SELECT_ALL = "SELECT * FROM CINEMA";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Cinema> findAll() {
        return jdbcTemplate.query(SELECT_ALL, (rs, rowNum) ->
                (new Cinema(
                        rs.getInt("id"),
                        rs.getString("NAME")
                )));
    }
}
