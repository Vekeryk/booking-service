package org.booking.controller;

import lombok.RequiredArgsConstructor;
import org.booking.model.Hotel;
import org.booking.service.HotelService;
import org.springframework.data.domain.PageRequest;
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
    public String allHotels(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "6") Integer size,
                            Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        model.addAttribute("hotelPage", hotelService.getAllPaginated(pageRequest));
        return "hotels";
    }

    @GetMapping(params = {"search"})
    public String searchHotels(@RequestParam String search,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "6") Integer size,
                               Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        model.addAttribute("hotelPage", hotelService.getByCitySearchPaginated(search, pageRequest));
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

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String deleteHotel(@RequestParam("hotel_id") Long hotelId) {
        hotelService.delete(hotelId);
        return "redirect:/hotels";
    }
}
