package org.booking.controller;

import lombok.RequiredArgsConstructor;
import org.booking.model.Room;
import org.booking.service.HotelService;
import org.booking.service.RoomService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("hotels/{hotel_id}/rooms")
public class RoomController {

    private final HotelService hotelService;
    private final RoomService roomService;

    @GetMapping
    public String rooms(@PathVariable("hotel_id") Long hotelId,
                        Model model) {
        model.addAttribute("hotel", hotelService.readById(hotelId));
        model.addAttribute("rooms", roomService.getAllByHotelId(hotelId));
        return "rooms";
    }

    @GetMapping("/available-rooms")
    public String searchAvailableRooms(@PathVariable("hotel_id") Long hotelId,
                                 Model model) {
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("tomorrow", LocalDate.now().plusDays(1));
        model.addAttribute("hotel", hotelService.readById(hotelId));
        return "available_rooms";
    }

    @GetMapping(value="/available-rooms", params = {"check_in", "check_out"})
    public String showAvailableRooms(@PathVariable("hotel_id") Long hotelId,
                 @RequestParam("check_in") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                 @RequestParam("check_out") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
                 Model model) {
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("check_in", checkIn);
        model.addAttribute("check_out", checkOut);
        model.addAttribute("hotel", hotelService.readById(hotelId));
        model.addAttribute("rooms", roomService.getAllByHotelId(hotelId));
        return "available_rooms";
    }

//    @GetMapping
//    public String manageRooms(@PathVariable("hotel_id") Long hotelId) {
//        return "manage_rooms";
//    }

//    @GetMapping(params = {"check_in", "check_out"})
//    public String manageRooms(@PathVariable("hotel_id") Long hotelId,
//                                 @RequestParam("check_in") LocalDate checkIn,
//                                 @RequestParam("check_out") LocalDate checkOut) {
//        return "available_rooms";
//    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String addRoom(@PathVariable("hotel_id") Long hotelId,
                          Model model) {
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("room", new Room());
        return "add_room";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String addRoom(@PathVariable("hotel_id") Long hotelId,
                          @ModelAttribute Room room) {
        roomService.create(room, hotelId);
        return "redirect:/hotels/" + hotelId+ "/rooms";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String deleteRoom(@PathVariable("hotel_id") Long hotelId,
                             @RequestParam("room_id") Long roomId) {
        roomService.delete(roomId);
        return "redirect:/hotels/" + hotelId+ "/rooms";
    }
}
