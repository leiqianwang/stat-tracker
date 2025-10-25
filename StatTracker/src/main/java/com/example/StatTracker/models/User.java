package com.example.StatTracker.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    //This class represents User table and all the associated info with it, now need to add necessary properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;

    //Annotaions for relationships on many to many between User and Player tables
    @ManyToMany
    @JoinTable(
            name="users_players",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns =  @JoinColumn(name="player_id")
    )
    private Set<User> favoritePlayers;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }

    public User(Role role, Set<User> favoritePlayers, String password, String username, int userId) {
        this.role = role;
        this.favoritePlayers = favoritePlayers;
        this.password = password;
        this.username = username;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<User> getFavoritePlayers() {
        return favoritePlayers;
    }

    public void setFavoritePlayers(Set<User> favoritePlayers) {
        this.favoritePlayers = favoritePlayers;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
