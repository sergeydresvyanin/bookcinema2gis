package com.gis.test.bookcinema.repoTest;


import com.gis.test.bookcinema.db.model.Place;
import com.gis.test.bookcinema.db.repo.PlacesRepo;
import com.gis.test.bookcinema.exception.BookingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts = {"/DATA.sql", "/bookplaceupdates.sql"})
public class PlacesRepoTest {
    @Autowired
    private PlacesRepo placesRepo;

    @Autowired
    private DataSource dataSource;

    @Test
    public void findById() {
        Place p = placesRepo.loadPlaceById(1L);
        assertFalse(p.getFree());
        assertEquals(1, (int) p.getPlaceNum());
        assertEquals(p.getRow(), 1);
    }

    @Test
    public void findByFilmSession() {
        List<Place> pList = placesRepo.loadPlacesByFilmSession(1L);
        assertEquals(6, pList.size());
        Place one = placesRepo.loadPlaceById(1L);
        Place two = placesRepo.loadPlaceById(2L);
        Place three = placesRepo.loadPlaceById(3L);
        assertTrue(pList.contains(one));
        assertTrue(pList.contains(two));
        assertTrue(pList.contains(three));
    }


    @Test
    public void bookPlaces() throws BookingException {
        placesRepo.bookPlaces(1L, Arrays.asList(2L, 3L));
        Place one = placesRepo.loadPlaceById(2L);
        Place two = placesRepo.loadPlaceById(3L);
        assertFalse(one.getFree());
        assertFalse(two.getFree());
    }

    @Test
    public void bookPlacesError() {
        try {
            placesRepo.bookPlaces(1L, Arrays.asList(4L));
            placesRepo.bookPlaces(1L, Arrays.asList(4L, 5L));
            fail();
        } catch (BookingException e) {
            Place one = placesRepo.loadPlaceById(5L);
            assertTrue(one.getFree());
        }
    }


    @Test
    public void personsPlaces() {
        List<Place> list = placesRepo.loadPersonFeaturePlaces(1L);
        Place one = placesRepo.loadPlaceById(1L);
        assertEquals(1, list.size());
        assertTrue(list.contains(one));
    }


}
