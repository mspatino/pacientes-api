package com.consultorio.pacientes.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.consultorio.pacientes.entities.Role;

public interface RoleRepository extends CrudRepository<Role,Long>{
     Optional<Role> findByName(String name);

}
