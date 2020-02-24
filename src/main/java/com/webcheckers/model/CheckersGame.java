package com.webcheckers.model;

import com.webcheckers.appl.PlayerLobby;
import sun.rmi.runtime.Log;

import java.util.logging.Logger;

/**
 * A single "Checkers game".
 *
 */
public class CheckersGame {
    private static final Logger LOG = Logger.getLogger(CheckersGame.class.getName());

    public CheckersGame() {
        LOG.fine("Game created ");
    }

}
