package com.gis.test.bookcinema.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gis.test.bookcinema.db.model.Cinema;
import com.gis.test.bookcinema.db.model.FilmSession;
import com.gis.test.bookcinema.db.model.Hall;
import com.gis.test.bookcinema.db.model.Place;
import com.gis.test.bookcinema.exception.BookingException;
import com.gis.test.bookcinema.exception.WrongPhoneNumber;
import com.gis.test.bookcinema.rest.model.PersonWithPlaces;
import com.gis.test.bookcinema.service.BookingPlaceService;
import com.gis.test.bookcinema.service.CinemaScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;


@RestController
public class Controller {
    @Autowired
    private BookingPlaceService bookingPlaceService;
    @Autowired
    private CinemaScheduleService cinemaScheduleService;

    @PostMapping("/place/book")
    public ResponseEntity<ObjectNode> bookPlace(@RequestBody PersonWithPlaces personWithPlaces) {
        try {
            bookingPlaceService.bookPlaces(personWithPlaces.getPhone(), personWithPlaces.getPlaces());
        } catch (BookingException | WrongPhoneNumber e) {
            ObjectNode error = (new ObjectMapper()).createObjectNode();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/place/unbook")
    public ResponseEntity<ObjectNode> unbookPlace(@RequestBody PersonWithPlaces personWithPlaces) {
        try {
            bookingPlaceService.unBookPlaces(personWithPlaces.getPhone(), personWithPlaces.getPlaces());
        } catch (BookingException | WrongPhoneNumber e) {
            ObjectNode error = (new ObjectMapper()).createObjectNode();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("place/booked")
    public ResponseEntity getPLaces(@RequestParam("phone") String phone) {
        try {
            return ResponseEntity.ok(bookingPlaceService.loadPersonFeaturePlaces(phone));
        } catch (WrongPhoneNumber wrongPhoneNumber) {
            ObjectNode error = (new ObjectMapper()).createObjectNode();
            error.put("error", wrongPhoneNumber.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }
    }

    @GetMapping("/cinema")
    public ResponseEntity<List<Cinema>> getCinemaList() {
        return ResponseEntity.ok(cinemaScheduleService.getCinemaList());
    }

    @GetMapping("/cinema/{cinemaId}/schedule")
    public ResponseEntity<List<Hall>> getFullSchedule(@PathVariable("cinemaId") Integer cinema, @RequestParam("onDate") Date onDate) {
        return ResponseEntity.ok(cinemaScheduleService.getOnDateSchedule(cinema, onDate));
    }

    @GetMapping("/cinema/{cinemaId}/hall")
    public ResponseEntity<List<Hall>> getCinemaList(@PathVariable("cinemaId") Integer cinemaId) {
        return ResponseEntity.ok(cinemaScheduleService.getHallList(cinemaId));
    }

    @GetMapping("/hall/{hallId}/actualfilmsession")
    public ResponseEntity<List<FilmSession>> getActualFilmSession(@PathVariable("hallId") Integer cinemaId,
                                                                  @RequestParam("onDate") Date onDate) {
        return ResponseEntity.ok(cinemaScheduleService.getActualFilmSession(cinemaId, onDate));
    }

    @GetMapping("/hall/{hallId}/filmsession")
    public ResponseEntity<List<FilmSession>> getFilmSession(@PathVariable("hallId") Integer cinemaId,
                                                            @RequestParam("dateFrom") Date dateFrom,
                                                            @RequestParam("dateTo") Date dateTo) {
        return ResponseEntity.ok(cinemaScheduleService.getFilmSession(cinemaId, dateFrom, dateTo));
    }

    @GetMapping("filmsession/{filmsessionId}/places")
    public ResponseEntity<List<Place>> getPLaces(@PathVariable("filmsessionId") Long filmSession) {
        return ResponseEntity.ok(cinemaScheduleService.getPlaces(filmSession));
    }

}
