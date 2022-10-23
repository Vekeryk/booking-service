package org.booking.service;

import lombok.RequiredArgsConstructor;
import org.booking.model.Booking;
import org.booking.repository.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking readById(long id) {
        return bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Бронювання з ідентифікатором " + id + " не знайдено"));
    }

    public Booking create(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void delete(long id) {
        bookingRepository.delete(readById(id));
    }

    public Page<Booking> getAllCurrentByUserId(long userId, Pageable pageable) {
        return bookingRepository.getAllCurrentByUserId(userId, LocalDate.now(), pageable);
    }
}
