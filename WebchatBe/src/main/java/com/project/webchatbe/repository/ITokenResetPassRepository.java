package com.project.webchatbe.repository;

import com.project.webchatbe.entity.TokenResetPass;
import com.project.webchatbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITokenResetPassRepository extends JpaRepository<TokenResetPass, Long> {

    Optional<TokenResetPass> findByToken(String token);

    void deleteByUser(User user);
}
