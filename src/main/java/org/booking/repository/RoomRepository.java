package org.booking.repository;

import org.booking.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> getAllByHotelId(long id, Pageable pageable);

    @Query(value = "select * " +
                    "from rooms " +
                    "    left join " +
                    "        (select b.room_id " +
                    "            from bookings b " +
                    "            where check_out > :check_in " +
                    "            and check_in < :check_out " +
                    "        ) occupied on id = occupied.room_id " +
                    "where hotel_id = :hotel_id " +
                    "  and occupied.room_id is null " +
                    "  and (:check_in < :check_out)",
            countQuery = "select count(id) from rooms " +
                    "    left join " +
                    "        (select b.room_id " +
                    "            from bookings b " +
                    "            where check_out > :check_in " +
                    "            and check_in < :check_out " +
                    "        ) occupied on id = occupied.room_id " +
                    "where hotel_id = :hotel_id " +
                    "  and occupied.room_id is null" +
                    "  and (:check_in < :check_out)",
            nativeQuery = true)
    Page<Room> getAvailableRooms(@Param("hotel_id") Long hotelId,
                                 @Param("check_in") LocalDate checkIn,
                                 @Param("check_out") LocalDate checkOut,
                                 Pageable pageable);
}
