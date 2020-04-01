package com.webcheckers.model;

//import sun.rmi.runtime.Log;

import java.util.logging.Logger;

/**
 * A single "Checkers game".
 *
 */
public class Game {
    private static final Logger LOG = Logger.getLogger(Game.class.getName());

    private Board board;

    private Player whitePlayer;
    private Player redPlayer;

    public Game(Player whitePlayer, Player redPlayer) {
        LOG.fine("Game started ");
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new Board();
    }

    public Game(Player whitePlayer){
        LOG.fine("Game Created");
        this.whitePlayer = whitePlayer;
        this.board = new Board();
    }

    public synchronized void addRedPlayer(Player redPlayer){
        this.redPlayer = redPlayer;
    }

    public synchronized Player getRedPlayer() {
        return redPlayer;
    }

    public synchronized Player getWhitePlayer() {
        return whitePlayer;
    }
}
