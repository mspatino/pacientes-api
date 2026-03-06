package com.consultorio.pacientes.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultorio.pacientes.entities.Role;
import com.consultorio.pacientes.entities.User;
import com.consultorio.pacientes.repositories.RoleRepository;
import com.consultorio.pacientes.repositories.UserRepository;
import com.consultorio.pacientes.services.UserService;



@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private RoleRepository roleRepository;  

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
     @Transactional(readOnly = true)
    public Iterable<User> findAll() {
             return (Iterable<User>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)    
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)  
    public Optional<User> findByUsername(String username) {
         return repository.findByUsername(username);
    }

    @Override
    @Transactional
    public User save(User user) {
        if (user.getEnabled() == null) {
        user.setEnabled(true); // valor por defecto
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(getRoles(user));
        return repository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(User user, Long id) {
       return this.findById(id)
        .map(u -> {
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());

            if (user.getEnabled() != null) {
            u.setEnabled(user.getEnabled());
            }
            u.setRoles(getRoles(user));
            return Optional.of(repository.save(u)); 
        })
        .orElseGet(()-> Optional.empty());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
     repository.deleteById(id);
    }

         
    private List<Role> getRoles(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        roleOptional.ifPresent(role -> roles.add(role));

        if(user.isAdmin()){
        Optional<Role> roleOptAdmin = roleRepository.findByName("ROLE_ADMIN");
        roleOptAdmin.ifPresent(roles::add);
        }
       
        return roles;
    }   

}
