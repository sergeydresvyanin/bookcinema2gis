package com.gis.test.bookcinema.db.repo;

import com.gis.test.bookcinema.db.model.Place;
import com.gis.test.bookcinema.exception.BookingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;


import java.sql.Types;
import java.util.List;

@Repository
public class PlacesRepo {
    private static final String SELECT_PLACE_BY_FILMSESSION = "SELECT * FROM PLACES WHERE REFFILMSESSION = ?";
    private static final String SELECT_PLACE_BY_ID = "SELECT * FROM PLACES WHERE ID = ?";
    private static final String SELECT_PERSON_PLACES = "SELECT distinct pl.*  FROM PLACES pl \n" +
            "INNER JOIN filmsession FS ON FS.id = PL.reffilmsession\n" +
            "WHERE refpersons = ? AND FS.start>CURRENT_TIMESTAMP";
    private static final String SELECT_PLACE_VERSION = "SELECT VERSION FROM PLACES WHERE ID = ?";
    private static final String SET_PLACE = "UPDATE PLACES SET VERSION= VERSION+1, REFPERSONS=?, change_time=CURRENT_TIMESTAMP WHERE VERSION = ? AND ID = ? AND REFPERSONS IS NULL";
    private static final String SET_PLACE_NULL = "UPDATE PLACES SET VERSION= VERSION+1, REFPERSONS=null, change_time=CURRENT_TIMESTAMP WHERE VERSION = ? AND REFPERSONS = ? AND ID = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;


    public Place loadPlaceById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_PLACE_BY_ID, new Object[]{id}, new int[]{Types.BIGINT}, (rs, rowNum) ->
                (new Place(
                        rs.getLong("id"),
                        rs.getLong("REFPERSONS") == 0,
                        rs.getInt("ROW"),
                        rs.getInt("PLACENUM")
                )));
    }

    public void bookPlaces(Long personId, List<Long> placeid) throws BookingException {
        DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = platformTransactionManager.getTransaction(paramTransactionDefinition);
        for (Long x : placeid) {
            Integer version = jdbcTemplate.queryForObject(SELECT_PLACE_VERSION, new Object[]{x}, new int[]{Types.BIGINT}, Integer.class);
            if (jdbcTemplate.update(SET_PLACE, version, personId, x) == 0) {
                Place p = loadPlaceById(x);
                platformTransactionManager.rollback(status);
                throw new BookingException(String.format("Место %s в ряду %s уже забронировано", p.getRow(), p.getPlaceNum()));
            }
        }
        platformTransactionManager.commit(status);
    }


    public void unbookPlaces(Long personId, List<Long> placeid) throws BookingException {
        for (Long x : placeid) {
            Integer version = jdbcTemplate.queryForObject(
                    SELECT_PLACE_VERSION, new Object[]{x}, new int[]{Types.BIGINT}, Integer.class);
            if (jdbcTemplate.update(SET_PLACE_NULL, version, personId, x) == 0) {
                Place p = loadPlaceById(x);
                throw new BookingException(String.format("Место %s в ряду %s, бронирование уже отменено", p.getRow(), p.getPlaceNum()));
            }
        }
    }

    public List<Place> loadPlacesByFilmSession(Long filmSession) {
        return jdbcTemplate.query(SELECT_PLACE_BY_FILMSESSION, new Object[]{filmSession}, new int[]{Types.BIGINT}, (rs, rowNum) ->
                (new Place(
                        rs.getLong("id"),
                        rs.getLong("REFPERSONS") == 0,
                        rs.getInt("ROW"),
                        rs.getInt("PLACENUM")
                )));
    }


    /**
     * Список предстоящих бронирований
     *
     * @param personId ид клиента
     * @return список забронировнных мест
     */
    public List<Place> loadPersonFeaturePlaces(Long personId) {
        return jdbcTemplate.query(SELECT_PERSON_PLACES, new Object[]{personId}, new int[]{Types.BIGINT}, (rs, rowNum) ->
                (new Place(
                        rs.getLong("id"),
                        rs.getLong("REFPERSONS") == 0,
                        rs.getInt("ROW"),
                        rs.getInt("PLACENUM")
                )));
    }
}
