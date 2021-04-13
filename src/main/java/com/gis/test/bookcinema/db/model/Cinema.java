package com.gis.test.bookcinema.db.model;

import java.util.List;

public class Cinema {
    private Integer id;
    private String name;
    private List<Hall> hallList;

    public Cinema() {
    }

    public Cinema(Integer id, String name) {
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

    public List<Hall> getHallList() {
        return hallList;
    }

    public void setHallList(List<Hall> hallList) {
        this.hallList = hallList;
    }
}
