package com.gis.test.bookcinema;

import com.gis.test.bookcinema.db.model.Cinema;
import com.gis.test.bookcinema.db.model.FilmSession;
import com.gis.test.bookcinema.db.model.Hall;
import com.gis.test.bookcinema.service.CinemaScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Sql("/DATA.sql")
@SpringBootTest
public class ScheduleTest {
    @Autowired
    private CinemaScheduleService cinemaScheduleService;

    @Test
    public void getCinemaList() {
        List<Cinema> cinemas = cinemaScheduleService.getCinemaList();
        assertEquals(3, cinemas.size());
        assertEquals(1, cinemas.get(0).getId());
    }

    @Test
    public void getHall() {
        List<Hall> halls = cinemaScheduleService.getHallList(3);
        assertEquals(2, halls.size());
        assertEquals(7, halls.get(0).getId());
    }

    @Test
    public void getFilmSessionByHallIdAndIntervalNegative() {
        Calendar from = Calendar.getInstance();
        from.set(2021, Calendar.APRIL, 11, 22, 0, 0);

        Calendar to = Calendar.getInstance();
        to.set(2021, Calendar.APRIL, 11, 24, 0, 0);

        List<FilmSession> cinemas = cinemaScheduleService.getFilmSession(1, from.getTime(), to.getTime());
        assertTrue(cinemas.isEmpty());
    }

    @Test
    public void getActualFilmSession() {
        List<FilmSession> filmSessions = cinemaScheduleService.getActualFilmSession(5, new Date());
        assertEquals(1, filmSessions.size());
        assertEquals(7, filmSessions.get(0).getId());
    }

    @Test
    public void getFilmSessionByHallIdAndIntervalPositive2() {
        Calendar to = Calendar.getInstance();
        to.set(Calendar.HOUR, 24);
        to.set(Calendar.MINUTE, 0);
        to.set(Calendar.SECOND, 0);

        Calendar from = Calendar.getInstance();
        from.set(Calendar.HOUR, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);


        List<FilmSession> filmSessions = cinemaScheduleService.getFilmSession(1, from.getTime(), to.getTime());
        assertEquals(3, filmSessions.size());
        assertEquals(2, filmSessions.get(1).getId());
    }

    @Test
    public void getScheduleOnDate() {
        List<Hall> halls = cinemaScheduleService.getOnDateSchedule(1, new Date());
        assertEquals(3, halls.size());

        assertFalse(halls.get(0).getFilmSessionList().isEmpty());
        assertFalse(halls.get(1).getFilmSessionList().isEmpty());
        assertTrue(halls.get(2).getFilmSessionList().isEmpty());

        assertEquals(1, halls.get(0).getFilmSessionList().get(0).getId());
    }

}
