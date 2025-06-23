package com.gestion.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.gestion.location.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

