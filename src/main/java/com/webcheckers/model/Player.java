package com.webcheckers.model;

public class Player {
    private final int playerID; // Internal ID constructed from player's information

    public Player(String username, String password) {
        playerID = username.hashCode() + password.hashCode();
    }

    public int getPlayerID() {
        return playerID;
    }
}
