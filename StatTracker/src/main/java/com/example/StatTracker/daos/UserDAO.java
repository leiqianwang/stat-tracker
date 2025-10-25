package com.example.StatTracker.daos;

import com.example.StatTracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserDAO extends JpaRepository<User,Integer> {

    /*
    This adds basic CRUD operations for User entity without needing to explicitly define them.
    Additional custom queries can be added here as needed.
     */

    Optional<User> findUserByUsername(String username);
}
