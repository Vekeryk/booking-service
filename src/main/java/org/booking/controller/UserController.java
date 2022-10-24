package org.booking.controller;

import lombok.RequiredArgsConstructor;
import org.booking.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String users(@PageableDefault(size = 6) Pageable pageable,
                        Model model) {
        model.addAttribute("userPage", userService.getAllPaginated(pageable));
        return "users";
    }

    @GetMapping(params = "email")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String searchUser(@RequestParam String email) {
        long userId = userService.findByEmail(email).getId();
        return "redirect:/users/" + userId + "/booking";
    }
}
