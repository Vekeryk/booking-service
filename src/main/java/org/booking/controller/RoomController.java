package org.booking.controller;

import lombok.RequiredArgsConstructor;
import org.booking.model.Room;
import org.booking.service.HotelService;
import org.booking.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
//    @GetMapping
//    public String availableRooms(@PathVariable("hotel_id") Long hotelId) {
//        return "available_rooms";
//    }

//    @GetMapping(params = {"check_in", "check_out"})
//    public String availableRooms(@PathVariable("hotel_id") Long hotelId,
//                                 @RequestParam("check_in") LocalDate checkIn,
//                                 @RequestParam("check_out") LocalDate checkOut) {
//        return "available_rooms";
//    }

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
    public String addRoom() {
        return "add_room";
    }

    @PostMapping("/add")
    public String addRoom(@PathVariable("hotel_id") Long hotelId,
                           @ModelAttribute Room room) {
//        roomController.create(hotel);
        return "redirect:/hotel/" + hotelId+ "/rooms";
    }
}
