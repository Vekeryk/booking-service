package org.booking.service;

import lombok.RequiredArgsConstructor;
import org.booking.model.Hotel;
import org.booking.repository.HotelRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public Hotel readById(long id) {
        return hotelRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Hotel with id " + id + " not found"));
    }
    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public void delete(long id) {
        hotelRepository.delete(readById(id));
    }

    public List<Hotel> getByCitySearch(String citySearch) {
        List<Hotel> hotels = hotelRepository.findByCityContainingIgnoreCase(citySearch);
        return hotels.isEmpty() ? new ArrayList<>() : hotels;
    }

    public List<Hotel> getAll() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.isEmpty() ? new ArrayList<>() : hotels;
    }
}
