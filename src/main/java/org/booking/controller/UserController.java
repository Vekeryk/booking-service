package org.booking.controller;

import lombok.RequiredArgsConstructor;
import org.booking.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public String users(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "6") Integer size,
                        Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        model.addAttribute("userPage", userService.getAllPaginated(pageRequest));
        return "users";
    }

    @GetMapping(params = "email")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String searchUser(@RequestParam String email) {
        long userId = userService.findByEmail(email).getId();
        return "redirect:/users/" + userId + "/booking";
    }
}
