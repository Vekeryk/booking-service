package org.booking.controller;

import lombok.RequiredArgsConstructor;
import org.booking.model.Hotel;
import org.booking.service.HotelService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public String hotels(@RequestParam(required = false) String citySearch, Model model) {
        if (citySearch == null) {
            model.addAttribute("hotels", hotelService.getAll());
        } else {
            model.addAttribute("hotels", hotelService.getByCitySearch(citySearch));
        }
        return "hotels";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String addHotel(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "add_hotel";
    }
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String addHotel(@ModelAttribute Hotel hotel) {
        hotelService.create(hotel);
        return "redirect:/hotels";
    }
}
