package com.consultorio.pacientes.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.pacientes.entities.User;
import com.consultorio.pacientes.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService service;

        @GetMapping
    public ResponseEntity<Iterable<User>> findAll() {
        logger.info("Llamada al metodo del UserController::findAll()");

        Iterable<User> users= service.findAll();
        if (!users.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Llamada al metodo del UserController::getUserById {}",id);
     
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        logger.info("Llamada al metodo del UserController::getUserByUsername {}",username);
      
        return service.findByUsername(username)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
        /**ptional<User> opt = service.findByUsername(username);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.notFound().build(); */
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        logger.info("Usuario creado: {}",user);
        
        User savedUser = service.save(user);
        // return ResponseEntity.ok(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @Valid @RequestBody User user,
            @PathVariable Long id) {
        logger.info("Usuario actualizado: {}",user);

     Optional<User> userActualizado =  service.update(user, id);

    return userActualizado
        .map(userUpdate -> ResponseEntity.ok(userUpdate))
        .orElseGet(() -> ResponseEntity.notFound().build());
    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Usuario eliminado: {}",id);
        
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
