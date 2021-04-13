package com.gis.test.bookcinema.db.model;

public class Person {
    private Long id;
    private String userID;

    public Person() {
    }

    public Person(Long id, String userID) {
        this.id = id;
        this.userID = userID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
