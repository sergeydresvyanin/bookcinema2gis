package com.gis.test.bookcinema.rest.model;

import java.util.List;

public class PersonWithPlaces {
    private String phone;
    private List<Long> places;

    public PersonWithPlaces() {
    }

    public PersonWithPlaces(String phone, List<Long> places) {
        this.phone = phone;
        this.places = places;
    }

    public PersonWithPlaces(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Long> getPlaces() {
        return places;
    }

    public void setPlaces(List<Long> places) {
        this.places = places;
    }
}
