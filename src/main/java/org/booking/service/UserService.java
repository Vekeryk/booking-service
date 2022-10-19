package org.booking.service;

import lombok.RequiredArgsConstructor;
import org.booking.model.User;
import org.booking.repository.UserRepository;
import org.booking.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public User create(User user) {
        user.setRole(roleService.readById(1));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found!"));
        return new UserDetailsImpl(user);
    }

    private Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
