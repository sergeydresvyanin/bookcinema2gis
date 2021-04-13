package com.gis.test.bookcinema.db.repo;


import com.gis.test.bookcinema.db.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class PersonRepo {
    private static final String UPDATE_OR_INSERT_PERSON = "INSERT INTO PERSONS(PHONE) VALUES(?) " +
            " ON CONFLICT (PHONE) DO UPDATE SET PHONE=EXCLUDED.PHONE RETURNING ID";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Person findOrCreatePerson(String phone) {
        Long pid = jdbcTemplate.queryForObject(
                UPDATE_OR_INSERT_PERSON, new Object[]{phone}, new int[]{Types.VARCHAR}, Long.class);
        return new Person(pid, phone);
    }
}
