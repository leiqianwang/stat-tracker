package com.example.StatTracker.services;

import com.example.StatTracker.daos.UserDAO;
import com.example.StatTracker.daos.PlayerDAO;
import com.example.StatTracker.models.User;
import com.example.StatTracker.models.Player;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class UserService {


    private final UserDAO userDAO;
    private final PlayerDAO playerDAO;

    @Autowired
    public UserService(UserDAO userDAO, PlayerDAO playerDAO) {
        this.userDAO = userDAO;
        this.playerDAO = playerDAO;
    }

    // TODO LOGIN METHOD
    public User login(String username, String password) {
        //Look up the user by the username
        Optional<User> userOpt = userDAO.findUserByUsername(username);
        // Check to ensure the user exists and the password matches
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // or throw an exception
    }

    //  TODO ADD PLAYERS TO USER"S FAVORITES SET
    public User addPlayerToFavorites(String username, int playerId) {
        //Look up the user
        Optional<User> possibleUser = userDAO.findUserByUsername(username);

        //Look up the player by playerId
        Optional<Player> possiblePlayer = playerDAO.findById(playerId);

        // Validate if both exist
        User user = null;
        if (possibleUser.isPresent() && possiblePlayer.get() != null) {
            user = possibleUser.get();
            Player player = possiblePlayer.get();

            // Add player to the list of User's favorite players
            Set<User> favoritePlayers = user.getFavoritePlayers();
            favoritePlayers.add((User) user.getFavoritePlayers());
            user.setFavoritePlayers((Set<User>) player);

            // Save the updated user back to the database
            return userDAO.save(user);

        }

        return null; // or throw an exception
    }


    //TODO REMOVE PLAYERS FROM USER"S FAVORITES
     public User removePlayerFromFavorites(String username, int playerId) {
         //Look up the user
         Optional<User> possibleUser = userDAO.findUserByUsername(username);

         //Look up the player by playerId
         Optional<Player> possiblePlayer = playerDAO.findById(playerId);

         // Validate if both exist
         User user = null;
         if (possibleUser.isPresent() && possiblePlayer.get() != null) {
             user = possibleUser.get();
             Player player = possiblePlayer.get();

             // Remove player from the list of User's favorite players
             Set<User> favoritePlayers = user.getFavoritePlayers();
             favoritePlayers.remove((User) user.getFavoritePlayers());
             user.setFavoritePlayers((Set<User>) player);

             // Save the updated user back to the database
             return userDAO.save(user);

         }

         return null; // or throw an exception
     }

    // TODO FETCH A SPECIFIC USER FROM DATABASE BY USERNAME

           public User getUserByUsername(String username) {
               Optional<User> userOpt = userDAO.findUserByUsername(username);
               return userOpt.orElse(null); // or throw an exception if not found
           }
}