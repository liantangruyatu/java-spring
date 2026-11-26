package com.example.labmanagement.repository;

import com.example.labmanagement.model.User;
import com.example.labmanagement.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(RoleEnum role);
}
