package org.booking.service;

import lombok.RequiredArgsConstructor;
import org.booking.model.User;
import org.booking.repository.UserRepository;
import org.booking.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public User readById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Користувача з ідентифікатором " + id + " не знайдено"));
    }

    public User create(User user) {
        if (isEmailOccupied(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Електронна пошта " + user.getEmail() + " вже зайнята"
            );
        }
        user.setRole(roleService.readById(1));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Користувача не знайдено"));
        return new UserDetailsImpl(user);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Користувача з логіном " + email + " не знайдено"));
    }

    private boolean isEmailOccupied(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public Page<User> getAllPaginated(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
