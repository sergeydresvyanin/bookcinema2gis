package com.gis.test.bookcinema.db.model;

import java.util.List;

public class Hall {
    private Integer id;
    private String name;
    private List<FilmSession> filmSessionList;

    public Hall() {
    }

    public Hall(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FilmSession> getFilmSessionList() {
        return filmSessionList;
    }

    public void setFilmSessionList(List<FilmSession> filmSessionList) {
        this.filmSessionList = filmSessionList;
    }
}
