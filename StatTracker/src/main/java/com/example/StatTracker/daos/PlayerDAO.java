package com.example.StatTracker.daos;

import com.example.StatTracker.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerDAO extends JpaRepository<Player, Integer> {
}
