package com.gis.test.bookcinema.service;

import com.gis.test.bookcinema.db.model.Person;
import com.gis.test.bookcinema.db.model.Place;
import com.gis.test.bookcinema.db.repo.*;
import com.gis.test.bookcinema.exception.BookingException;
import com.gis.test.bookcinema.exception.WrongPhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingPlaceService {
    @Autowired
    private PersonRepo personRepo;
    @Autowired
    private PlacesRepo placesRepo;


    /**
     * @param userPhone номер телефоан пользователя, убираем из него все нецифровые символы
     * @param placesId  список мест для бронирования
     */
    public void bookPlaces(String userPhone, List<Long> placesId) throws BookingException, WrongPhoneNumber {
        if (userPhone != null && !userPhone.replaceAll("\\D", "").isEmpty()) {
            Person p = personRepo.findOrCreatePerson(userPhone.replaceAll("\\D", ""));
            placesRepo.bookPlaces(p.getId(), placesId);
        } else
            throw new WrongPhoneNumber("Телефон должен состоять из цифр и не должен быть пустым");
    }

    /**
     * @param userPhone номер телефоан пользователя, убираем из него все нецифровые символы
     * @param placesId  список мест для бронирования
     */
    public void unBookPlaces(String userPhone, List<Long> placesId) throws BookingException, WrongPhoneNumber {
        if (userPhone != null && !userPhone.replaceAll("\\D", "").isEmpty()) {
            Person p = personRepo.findOrCreatePerson(userPhone.replaceAll("\\D", ""));
            placesRepo.unbookPlaces(p.getId(), placesId);
        } else
            throw new WrongPhoneNumber("Телефон должен состоять из цифр и не должен быть пустым");
    }

    /**
     * @param userPhone номер телефоан пользователя, убираем из него все нецифровые символы
     */
    public List<Place> loadPersonFeaturePlaces(String userPhone) throws WrongPhoneNumber {
        if (userPhone != null) {
            Person p = personRepo.findOrCreatePerson(userPhone.replaceAll("\\D", ""));
            return placesRepo.loadPersonFeaturePlaces(p.getId());
        }
        throw new WrongPhoneNumber("Телефон должен состоять из цифр и не должен быть пустым");
    }
}
