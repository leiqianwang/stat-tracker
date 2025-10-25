package com.example.StatTracker.models;

import jakarta.persistence.*;


@Entity
@Table(name="players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playerId;
    private String name;
    private String picUrl;
    private int passYards;
    private int attempts;
    private int completions;
    private int touchdowns;
    private int interceptions;

    public Player() {
    }

    public Player(int attempts, int completions, int interceptions, String name, int passYards, int playerId, String picUrl, int touchdowns) {
        this.attempts = attempts;
        this.completions = completions;
        this.interceptions = interceptions;
        this.name = name;
        this.passYards = passYards;
        this.playerId = playerId;
        this.picUrl = picUrl;
        this.touchdowns = touchdowns;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getPassYards() {
        return passYards;
    }

    public void setPassYards(int passYards) {
        this.passYards = passYards;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getCompletions() {
        return completions;
    }

    public void setCompletions(int completions) {
        this.completions = completions;
    }

    public int getTouchdowns() {
        return touchdowns;
    }

    public void setTouchdowns(int touchdowns) {
        this.touchdowns = touchdowns;
    }

    public int getInterceptions() {
        return interceptions;
    }

    public void setInterceptions(int interceptions) {
        this.interceptions = interceptions;
    }
}
