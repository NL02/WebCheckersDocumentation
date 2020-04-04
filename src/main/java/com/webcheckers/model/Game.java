package com.webcheckers.model;

//import sun.rmi.runtime.Log;

import com.webcheckers.util.Message;

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
    private Color activeColor; // The color of the active player
    private Player activePlayer; // The player about to make a move

    public Game(Player whitePlayer, Player redPlayer) {
        LOG.fine("Game started ");
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new Board();
        this.activeColor = Color.RED;
        this.activePlayer = redPlayer;
    }

    public Game(Player whitePlayer){
        LOG.fine("Game Created");
        this.whitePlayer = whitePlayer;
        this.board = new Board();
        this.activeColor = Color.RED;
        redPlayer = null;
    }

    public Message validateMove(Move move) {
        return this.board.validateMove(move);
    }

    public synchronized void addRedPlayer(Player redPlayer){
        this.activePlayer = redPlayer;
        this.redPlayer = redPlayer;
    }

    /**
     * @return the color of the player whose turn it is
     */
    public Color getActiveColor() {
        return this.activeColor;
    }

    /**
     * @return the player whose turn it is
     */
    public Player getActivePlayer() {
        return activePlayer;
    }
    public synchronized Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Method used to switch the active color of the game
     */
    public synchronized void changeActiveColor() {
        if ( this.activeColor == Color.RED) {
            this.activeColor = Color.WHITE;
            this.activePlayer = whitePlayer;
        } else {
            this.activeColor = Color.RED;
            this.activePlayer = redPlayer;
        }
    }

    public synchronized Player getWhitePlayer() {
        return whitePlayer;
    }

    public synchronized void addBoard(Board board){
        this.board = board;
    }
}
