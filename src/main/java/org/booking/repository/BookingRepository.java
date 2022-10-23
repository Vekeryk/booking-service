package org.booking.repository;

import org.booking.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value="select * " +
            "from bookings " +
            "where user_id = :user_id " +
            "and check_out >= :today " +
            "order by check_out",
            countQuery = "select count(id) from bookings " +
                    "where user_id = :user_id " +
                    "and check_out >= :today",
            nativeQuery = true)
    Page<Booking> getAllCurrentByUserId(@Param("user_id") long userId,
                                        @Param("today") LocalDate today, Pageable pageable);
}
