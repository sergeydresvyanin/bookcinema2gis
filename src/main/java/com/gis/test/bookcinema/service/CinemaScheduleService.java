package com.gis.test.bookcinema.service;

import com.gis.test.bookcinema.db.model.Cinema;
import com.gis.test.bookcinema.db.model.FilmSession;
import com.gis.test.bookcinema.db.model.Hall;
import com.gis.test.bookcinema.db.model.Place;
import com.gis.test.bookcinema.db.repo.CinemaRepo;
import com.gis.test.bookcinema.db.repo.FilmSessionRepo;
import com.gis.test.bookcinema.db.repo.HallRepo;
import com.gis.test.bookcinema.db.repo.PlacesRepo;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CinemaScheduleService {
    @Autowired
    private CinemaRepo cinemaRepo;
    @Autowired
    private FilmSessionRepo filmSessionRepo;
    @Autowired
    private HallRepo hallRepo;
    @Autowired
    private PlacesRepo placesRepo;

    public List<Cinema> getCinemaList() {
        return cinemaRepo.findAll();
    }

    public List<Hall> getHallList(Integer cinemaId) {
        return hallRepo.findByCinema(cinemaId);
    }

    public List<FilmSession> getFilmSession(Integer hallId, Date from, Date to) {
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(to);
        toCalendar.set(Calendar.HOUR, 24);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        return filmSessionRepo.findByHallAndDateInterval(hallId, from, toCalendar.getTime());
    }

    public List<FilmSession> getActualFilmSession(Integer hallId, Date onDate) {
        Calendar to = Calendar.getInstance();
        to.setTime(onDate);
        to.set(Calendar.HOUR, 24);
        to.set(Calendar.MINUTE, 0);
        to.set(Calendar.SECOND, 0);

        Calendar from = Calendar.getInstance();
        from.setTime(onDate);
        if (onDate.compareTo(new Date()) > 0) {
            from.set(Calendar.HOUR, 0);
            from.set(Calendar.MINUTE, 0);
            from.set(Calendar.SECOND, 0);
        }
        return filmSessionRepo.findByHallAndDateInterval(hallId, from.getTime(), to.getTime());
    }

    public List<Hall> getOnDateSchedule(Integer cinemaId, Date onDate) {
        Calendar to = Calendar.getInstance();
        to.setTime(onDate);
        to.set(Calendar.HOUR, 24);
        to.set(Calendar.MINUTE, 0);
        to.set(Calendar.SECOND, 0);

        Calendar from = Calendar.getInstance();
        from.setTime(onDate);
        from.set(Calendar.HOUR, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);
        List<Hall> halls = hallRepo.findByCinema(cinemaId);
        halls.forEach(hall -> {
            List<FilmSession> fiList = filmSessionRepo.findByHallAndDateInterval(hall.getId(), from.getTime(), to.getTime());
            hall.setFilmSessionList(fiList);
        });
        return halls;
    }

    public List<Place> getPlaces(Long filmSessionId) {
        return placesRepo.loadPlacesByFilmSession(filmSessionId);
    }
}
