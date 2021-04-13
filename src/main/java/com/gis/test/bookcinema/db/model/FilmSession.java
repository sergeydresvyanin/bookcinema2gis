package com.gis.test.bookcinema.db.model;

import java.util.Date;

public class FilmSession {
    private Long id;
    private String name;
    private Date start;
    private Date dateEnd;
    private boolean isFinished;

    public FilmSession() {
    }

    public FilmSession(Long id, String name, Date start, Date dateEnd, boolean isFinished) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.dateEnd = dateEnd;
        this.isFinished = isFinished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
