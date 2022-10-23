package org.booking.controller;

import lombok.RequiredArgsConstructor;
import org.booking.model.Booking;
import org.booking.security.UserDetailsImpl;
import org.booking.service.BookingService;
import org.booking.service.RoomService;
import org.booking.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/users/{userId}/booking")
@RequiredArgsConstructor
public class BookingController {

    private final UserService userService;
    private final RoomService roomService;
    private final BookingService bookingService;

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER') or #userId == principal.user.id")
    public String booking(@PathVariable Long userId,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "8") Integer size,
                          Authentication authentication,
                          Model model) {
        if (((UserDetailsImpl) authentication.getPrincipal()).getId() != userId) {
            model.addAttribute("user", userService.readById(userId));
        }
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        model.addAttribute("bookingPage", bookingService.getAllCurrentByUserId(userId, pageRequest));
        return "booking";
    }

    @PostMapping("/room/{roomId}")
    @PreAuthorize("#userId == principal.user.id")
    public String bookRoom(@PathVariable Long userId,
                           @PathVariable Long roomId,
                           @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                           @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        Booking booking = Booking.builder()
                .user(userService.readById(userId))
                .room(roomService.readById(roomId))
                .checkIn(checkIn).checkOut(checkOut).build();
        bookingService.create(booking);
        return "redirect:/users/" + userId + "/booking";
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasAuthority('MANAGER') or #userId == principal.user.id")
    public String booking(@PathVariable Long userId,
                          @PathVariable Long bookingId) {
        bookingService.delete(bookingId);
        return "redirect:/users/" + userId + "/booking";
    }
}
