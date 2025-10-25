package com.example.StatTracker.services;

import com.example.StatTracker.daos.PlayerDAO;
import com.example.StatTracker.daos.UserDAO;
import com.example.StatTracker.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.StatTracker.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class PlayerService {

    private final PlayerDAO playerDAO;

    @Autowired
    public PlayerService(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    //TODO GET ALL PLAYERS
    public List<Player> getAllPlayers() {
        return playerDAO.findAll();
    }

    //TODO ADD NEW PLAYER
    public Player createNewPlayer(Player player) {
        return playerDAO.save(player);
    }

    //TODO update an existing player
    public Player updatePlayer(int playerId, Player updatedPlayer) {
        Optional<Player> existingPlayerOpt = playerDAO.findById(playerId);
        if (existingPlayerOpt.isPresent()) {
            Player existingPlayer = existingPlayerOpt.get();
            existingPlayer.setName(updatedPlayer.getName());
            existingPlayer.setPicUrl(updatedPlayer.getPicUrl());
            existingPlayer.setPassYards(updatedPlayer.getPassYards());
            existingPlayer.setAttempts(updatedPlayer.getAttempts());
            existingPlayer.setCompletions(updatedPlayer.getCompletions());
            existingPlayer.setTouchdowns(updatedPlayer.getTouchdowns());
            return playerDAO.save(existingPlayer);
        } else {
            return null; // or throw an exception
        }
    }
        //TODO delete a player
        public Player deletePlayer(int playerId) {
            Optional<Player> playerToDelete = playerDAO.findById(playerId);
            if (playerToDelete.isPresent()) {
                Player player = playerToDelete.get();
                playerDAO.delete(player);
                return player;
            } else {
                return null; // or throw an exception
            }

        }
}