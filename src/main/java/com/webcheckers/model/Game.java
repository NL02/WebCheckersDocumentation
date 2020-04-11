package com.webcheckers.model;

//import sun.rmi.runtime.Log;

import com.webcheckers.appl.Player;
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

    private String gameOverMsg = null;
    public Player winner = null;

    public Game(Player whitePlayer, Player redPlayer) {
        LOG.fine("Game Created");
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

    public Message backupMove() {
        return this.board.backupMove();
    }

    public Message submitTurn() {
        return this.board.submitTurn();
    }

    public synchronized void addRedPlayer(Player redPlayer){
        LOG.fine("Game started between " + whitePlayer.name + " and " + redPlayer.name);
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
        LOG.fine("Game Turn Toggled");
        if ( this.activeColor == Color.RED) {
            this.activeColor = Color.WHITE;
            this.activePlayer = whitePlayer;
        } else {
            this.activeColor = Color.RED;
            this.activePlayer = redPlayer;
        }
        board.changeActiveColor();
    }

    public void gameOver(String gameOverMsg, Player whoWon){
        this.winner = whoWon;
        this.gameOverMsg = gameOverMsg;
    }

    public String isGameOver(){
        return gameOverMsg;
    }

    public synchronized Player getWhitePlayer() {
        return whitePlayer;
    }

    public synchronized void addBoard(Board board){
        this.board = board;
    }

    public synchronized Board getBoard() {
        return this.board;
    }

    public void checkEndGame() {
        int pieces = 0;
        int validMoves = 0;
        Space space;
        for (int i = 0; i < 8; i++) {
            if (validMoves != 0) {
                break;
            }
            for (int j = 0; j < 8; j++) {
                if (validMoves != 0) {
                    break;
                }
                space = board.getBoard()[i][j];
                if (!space.isValid() || space.getPiece() == null || space.getPiece().getColor() != activeColor) {
                    continue;
                }
                pieces++;
                if (board.checkCanMove(i, j)) {
                    validMoves++;
                }
            }

        }
        if (validMoves != 0) {
            return;
        }
        Player winner = activePlayer == whitePlayer ? redPlayer : whitePlayer;
        Player loser = activePlayer == whitePlayer ? whitePlayer : redPlayer;
        if (pieces == 0) {
            gameOver(loser.name + " has no pieces remaining! " + winner.name + " is the winner!", winner);
        } else {
            gameOver(loser.name + " has no valid moves to make! " + winner.name + " is the winner!", winner);
        }
    }
}
