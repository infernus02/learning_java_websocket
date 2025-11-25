package com.project.webchatbe.repository;

import com.project.webchatbe.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);

    Optional<Role> findByName(String name);
}
