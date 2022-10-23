package org.booking.service;

import lombok.RequiredArgsConstructor;
import org.booking.model.Room;
import org.booking.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    public Room readById(long id) {
        return roomRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Кімнату з ідентифікатором " + id + " не знайдено"));
    }

    @Transactional
    public Room create(Room room, Long hotelId) {
        room.setHotel(hotelService.readById(hotelId));

        if (room.getHotel().getRooms().contains(room)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Така кімната вже існує");
        }
        return roomRepository.save(room);
    }

    public void delete(long id) {
        roomRepository.delete(readById(id));
    }

    public Page<Room> getAllByHotelIdPaginated(long id, Pageable pageable) {
        return roomRepository.getAllByHotelId(id, pageable);
    }

    public Page<Room> getAvailableRooms(long hotelId, LocalDate checkIn, LocalDate checkOut, Pageable pageable) {
        return roomRepository.getAvailableRooms(hotelId, checkIn, checkOut, pageable);
    }
}
