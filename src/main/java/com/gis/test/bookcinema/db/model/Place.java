package com.gis.test.bookcinema.db.model;

import java.util.Objects;

public class Place {
    private Long id;
    private Boolean isFree;
    private Integer row;
    private Integer placeNum;

    public Place() {
    }

    public Place(Long id, Boolean isFree, Integer row, Integer placeNum) {
        this.id = id;
        this.isFree = isFree;
        this.row = row;
        this.placeNum = placeNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(Integer placeNum) {
        this.placeNum = placeNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(id, place.id) &&
                Objects.equals(isFree, place.isFree) &&
                Objects.equals(row, place.row) &&
                Objects.equals(placeNum, place.placeNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isFree, row, placeNum);
    }
}
