package org.booking.service;

import lombok.RequiredArgsConstructor;
import org.booking.model.Room;
import org.booking.repository.RoomRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room readById(long id) {
        return roomRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Room with id " + id + " not found"));
    }
    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public void delete(long id) {
        roomRepository.delete(readById(id));
    }

    public List<Room> getAll() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.isEmpty() ? new ArrayList<>() : rooms;
    }

    public List<Room> getAllByHotelId(long id) {
        List<Room> rooms = roomRepository.getAllByHotelId(id);
        return rooms.isEmpty() ? new ArrayList<>() : rooms;
    }
}
