package com.webcheckers.model;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.board.BoardView;
//import sun.rmi.runtime.Log;

import java.util.logging.Logger;

/**
 * A single "Checkers game".
 *
 */
public class CheckersGame {
    private static final Logger LOG = Logger.getLogger(CheckersGame.class.getName());

    private CheckerBoard board;

    private Player whitePlayer;
    private Player redPlayer;

    public CheckersGame(Player whitePlayer, Player redPlayer) {
        LOG.fine("Game started ");
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new CheckerBoard();
    }

    public CheckersGame(Player whitePlayer){
        LOG.fine("Game Created");
        this.whitePlayer = whitePlayer;
        this.board = new CheckerBoard();
    }

    public void addRedPlayer(Player redPlayer){
        this.redPlayer = redPlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }
}
