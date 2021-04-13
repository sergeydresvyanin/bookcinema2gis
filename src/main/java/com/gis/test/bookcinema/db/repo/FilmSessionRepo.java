package com.gis.test.bookcinema.db.repo;

import com.gis.test.bookcinema.db.model.FilmSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class FilmSessionRepo {

    private static final String SELECT_ACTUAL_FILM_SESSION = "SELECT * FROM FILMSESSION WHERE REFHALL=? AND START BETWEEN  ? AND ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<FilmSession> findByHallAndDateInterval(Integer hallid, Date from, Date to) {
        return jdbcTemplate.query(SELECT_ACTUAL_FILM_SESSION, new Object[]{hallid, from, to}, new int[]{Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP}, (rs, rowNum) ->
                (new FilmSession(
                        rs.getLong("id"),
                        rs.getString("NAME"),
                        rs.getDate("START"),
                        rs.getDate("DATEEND"),
                        rs.getTimestamp("START").compareTo(new Date()) < 0
                )));
    }

}
