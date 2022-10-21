package org.booking.service;

import lombok.RequiredArgsConstructor;
import org.booking.model.Hotel;
import org.booking.repository.HotelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

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

    public Page<Hotel> getByCitySearchPaginated(String citySearch, Pageable pageable) {
        return hotelRepository.findByCityContainingIgnoreCase(citySearch, pageable);
    }

    public Page<Hotel> getAllPaginated(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }
}
