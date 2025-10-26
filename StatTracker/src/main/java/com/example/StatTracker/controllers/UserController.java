package com.example.StatTracker.controllers;

import com.example.StatTracker.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RestController;
import com.example.StatTracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    Authorization and Authentication (the two main concepts for security)

    Sessions: - server based
    - Store what user is logged in on the server side and send a session key to the client to be then reaccessed by their info/endpoints
    - More secure, but harder to scale

    Tokens: - client based
    -JWTs - JSON Web Tokens, which the backend will generate a token and send it back upon a successful login attempt.
    This token is stored by the frontend and decrypted by the backend on each request to verify the user's identity.
    - Easier to scale, but less secure
    - Adheres more to RESTful principles
     */

    //TODO LOGIN
    @PostMapping("/login") //http://localhost:8080/users/login
    public ResponseEntity<User> login(HttpSession session, @RequestBody User loginRequest) {
        //First need to validate the user's credentials or couldn't find the user with the specific username
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).build(); //401 Unauthorized

        }
        //If we reach here, the user has successfully logged in
        //Store their info in the session
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());
        session.setAttribute("userId", user.getUserId());
        return ResponseEntity.ok(user);
    }

    //TODO ADD PLAYER TO FAVORITES
    @PostMapping("/favorites/{playerId}") //http://localhost:8080
    public ResponseEntity<User> addPlayerToFavorites(HttpSession session, @PathVariable int playerId) {
        //Check if the user is logged in
        //Check if the session is brand new
        // Check if the user has a username stored
        if (session.isNew() || session.getAttribute("username") == null) {
            return ResponseEntity.status(401).build(); //401 Unauthorized
        }

        User returneddUser = userService.addPlayerToFavorites((String) session.getAttribute("username"), playerId);
        if (returneddUser == null) {
            return ResponseEntity.badRequest().build(); //404 Not Found
        }

        return ResponseEntity.ok(returneddUser);
    }

    //TODO REMOVE PLAYER FROM FAVORITES
    @DeleteMapping("/favorites/{playerId}") //http://localhost:8080/users/f
    public ResponseEntity<User> removePlayerFromFavorites(HttpSession session, @PathVariable int playerId) {
        //Check if the user is logged in
        if (session.isNew() || session.getAttribute("username") == null) {
            return ResponseEntity.status(401).build(); //401 Unauthorized
        }

        User returnedUser = userService.removePlayerFromFavorites((String) session.getAttribute("username"), playerId);
        if (returnedUser == null) {
            return ResponseEntity.badRequest().build(); //404 Not Found
        }

        return ResponseEntity.ok(returnedUser);
    }

    //TODO GET ALL USER INFO
    @GetMapping("/{username}") //http://localhost:8080/users/{username}
    public ResponseEntity<User> getUserInfo(HttpSession session, @PathVariable String username) {
        //Check if the user is logged in
        if (session.isNew() || session.getAttribute("username") == null) {
            return ResponseEntity.status(401).build(); //401 Unauthorized
        }
        User user = userService.getUserByUsername((String) session.getAttribute("username"));
        if (user == null) {
            return ResponseEntity.status(404).build(); //404 Not Found
        }

        return ResponseEntity.ok(user);
    }
}
