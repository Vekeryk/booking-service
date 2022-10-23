package org.booking.controller;

import lombok.RequiredArgsConstructor;
import org.booking.model.Room;
import org.booking.service.HotelService;
import org.booking.service.RoomService;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("hotels/{hotelId}/rooms")
public class RoomController {

    private final HotelService hotelService;
    private final RoomService roomService;

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public String rooms(@PathVariable Long hotelId,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "6") Integer size,
                        Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        model.addAttribute("hotel", hotelService.readById(hotelId));
        model.addAttribute("roomPage", roomService.getAllByHotelIdPaginated(hotelId, pageRequest));
        return "rooms";
    }

    @GetMapping("/available-rooms")
    public String searchAvailableRooms(@PathVariable Long hotelId,
                                       Model model) {
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("tomorrow", LocalDate.now().plusDays(1));
        model.addAttribute("hotel", hotelService.readById(hotelId));
        return "available_rooms";
    }

    @GetMapping(value = "/available-rooms", params = {"checkIn", "checkOut"})
    public String showAvailableRooms(@RequestParam(defaultValue = "1") Integer page,
                                     @PathVariable Long hotelId,
                                     @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                                     @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
                                     Model model) {
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("hotel", hotelService.readById(hotelId));
        PageRequest pageRequest = PageRequest.of(page - 1, 6);
        model.addAttribute("roomPage", roomService.getAvailableRooms(hotelId, checkIn, checkOut, pageRequest));
        return "available_rooms";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String addRoom(@PathVariable Long hotelId,
                          Model model) {
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("room", new Room());
        return "add_room";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String addRoom(@PathVariable Long hotelId,
                          @ModelAttribute Room room) {
        roomService.create(room, hotelId);
        return "redirect:/hotels/" + hotelId + "/rooms";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String deleteRoom(@PathVariable Long hotelId,
                             @RequestParam Long roomId) {
        roomService.delete(roomId);
        return "redirect:/hotels/" + hotelId + "/rooms";
    }
}
