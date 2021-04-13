package com.gis.test.bookcinema;

import com.gis.test.bookcinema.db.model.Place;
import com.gis.test.bookcinema.db.repo.PlacesRepo;
import com.gis.test.bookcinema.exception.BookingException;
import com.gis.test.bookcinema.exception.WrongPhoneNumber;
import com.gis.test.bookcinema.service.BookingPlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class PlacesServiceTest {
    @Autowired
    private BookingPlaceService bookingPlaceService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PlacesRepo placesRepo;

    @BeforeEach
    private void clearBdData() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/DATA.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @Test
    public void testPersonPlaces() throws WrongPhoneNumber {
        String userPhone = "79118757569";
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/bookplaceupdates.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
        List<Place> placeList = bookingPlaceService.loadPersonFeaturePlaces(userPhone);
//        assertTrue(placeList.size() == 1 && placeList.get(0).getId() == 2L);
    }

    @Test
    public void testTakeTwoPlacePositive() throws WrongPhoneNumber, BookingException {
        String userPhone = "79118757562";
        bookingPlaceService.bookPlaces(userPhone, Arrays.asList(3L, 4L));
        List<Place> placeList = bookingPlaceService.loadPersonFeaturePlaces(userPhone);
        Place one = placesRepo.loadPlaceById(3L);
        Place two = placesRepo.loadPlaceById(4L);

        assertEquals(2, placeList.size());
        assertTrue(placeList.contains(one));
        assertTrue(placeList.contains(two));
    }

    @Test
    public void testTakeTwoPlaceNegative() {
        String userPhone = "79118757562";
        try {
            bookingPlaceService.bookPlaces("79111727562", Arrays.asList(1L, 2L));
            bookingPlaceService.bookPlaces(userPhone, Arrays.asList(2L, 3L));
            fail();
        } catch (BookingException e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(), "Место 1 в ряду 2 уже забронировано");
        } catch (WrongPhoneNumber wrongPhoneNumber) {
            fail();
        }

    }


    @Test
    public void testWrongPhone1() {
        try {
            bookingPlaceService.bookPlaces(null, Arrays.asList(2L, 3L));
            fail();
        } catch (BookingException e) {
            fail();
        } catch (WrongPhoneNumber wrongPhoneNumber) {
            assertEquals(wrongPhoneNumber.getMessage(), "Телефон должен состоять из цифр и не должен быть пустым");
        }
    }

    @Test
    public void testWrongPhone2() {
        try {
            bookingPlaceService.bookPlaces("", Arrays.asList(2L, 3L));
            fail();
        } catch (BookingException e) {
            fail();
        } catch (WrongPhoneNumber wrongPhoneNumber) {
            assertEquals(wrongPhoneNumber.getMessage(), "Телефон должен состоять из цифр и не должен быть пустым");
        }
    }

    @Test
    public void testWrongPhone3() {
        try {
            bookingPlaceService.bookPlaces("sda", Arrays.asList(2L, 3L));
            fail();
        } catch (BookingException e) {
            fail();
        } catch (WrongPhoneNumber wrongPhoneNumber) {
            assertEquals(wrongPhoneNumber.getMessage(), "Телефон должен состоять из цифр и не должен быть пустым");
        }
    }

}
