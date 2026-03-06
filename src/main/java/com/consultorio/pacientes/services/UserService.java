package com.consultorio.pacientes.services;

import java.util.Optional;

import com.consultorio.pacientes.entities.User;

public interface UserService {

    Iterable<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    User save(User user);
    Optional<User> update(User user, Long id);
    void deleteById(Long id);

}
