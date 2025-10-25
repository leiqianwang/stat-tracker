package com.example.StatTracker.controllers;

import com.example.StatTracker.models.Player;
import com.example.StatTracker.models.Role;

import com.example.StatTracker.services.PlayerService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

import java.util.List;


@RestController
@RequestMapping("players")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // TODO view all Players
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        return new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    }

    //TODO ADD A NEW PLAYER (ADMIN ONLY)
    @PostMapping
    public ResponseEntity<Player> createPlayer(HttpSession session, @RequestBody Player player) {
        //Check if the user is actually logged in
        if (session.isNew() || session.getAttribute("username") == null) {
            //the user is not logged in
            return ResponseEntity.status(401).build();

            //Not to be confused with 403 forbidden, 401 means unauthorized (not logged in)
        }
        // Now we know the user is logged in, but we need to ensure they have the right permissions (ADMIN)
        if (session.getAttribute("role") != Role.ADMIN) {
            //This means the user is signed as not an admin
            return ResponseEntity.status(403).build(); //403 Forbidden
        }

        // After passing both validation checks, the user is logged in and is an admin

        //Add the player to the database
        Player addPlayer = playerService.createNewPlayer(player);
        return new ResponseEntity<>(addPlayer, HttpStatus.CREATED);
    }

    //TODO UPDATE AN EXISTING PLAYER (ADMIN ONLY)
    @PutMapping("/{playerId}")
    public ResponseEntity<Player> updatePlayer(HttpSession session, @PathVariable int playerId, @RequestBody Player player) {
        //Check if the user is actually logged in
        if (session.isNew() || session.getAttribute("username") == null) {
            //the user is not logged in
            return ResponseEntity.status(401).build();
        }
        if (session.getAttribute("role") != Role.ADMIN) {
            return ResponseEntity.status(403).build();
        }

        Player updatedPlayer = playerService.updatePlayer(playerId, player);
        if (updatedPlayer != null) {
            return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    //TODO DELETE A PLAYER (ADMIN ONLY)
    @DeleteMapping("/{playerId}")
    public ResponseEntity<Player> deletePlayer(HttpSession session, @PathVariable int playerId) {
        //Check if the user is actually logged in
        if (session.isNew() || session.getAttribute("username") == null) {
            //the user is not logged in
            return ResponseEntity.status(401).build();
        }
        if (session.getAttribute("role") != Role.ADMIN) {
            return ResponseEntity.status(403).build();
        }
        Player deletedPlayer = playerService.deletePlayer(playerId);
        if (deletedPlayer != null) {
            return new ResponseEntity<>(deletedPlayer, HttpStatus.OK);
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}