package org.booking.service;

import lombok.RequiredArgsConstructor;
import org.booking.model.Role;
import org.booking.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role readById(long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Роль з ідентифікатором " + id + " не знайдено"));
    }
}
