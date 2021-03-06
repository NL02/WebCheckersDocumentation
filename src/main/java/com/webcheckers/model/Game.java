package com.webcheckers.model;

//import sun.rmi.runtime.Log;

import com.webcheckers.appl.Player;
import com.webcheckers.util.Message;

import java.util.logging.Logger;

/**
 * A single "Checkers game".
 *
 * @author Wyatt Holcombe, Ethan Davison, Nelson Liang
 * Commented by: May Wu
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

    //TODO LIST OF ALL MOVES MADE

    /**
     * Constructor for game with two players
     * @param whitePlayer
     * @param redPlayer
     */
    public Game(Player whitePlayer, Player redPlayer) {
        LOG.fine("Game Created");
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = new Board();
        this.activeColor = Color.RED;
        this.activePlayer = redPlayer;
    }

    /**
     * Constructor for game with only one player
     * @param whitePlayer Player
     */
    public Game(Player whitePlayer){
        LOG.fine("Game Created");
        this.whitePlayer = whitePlayer;
        this.board = new Board();
        this.activeColor = Color.RED;
        redPlayer = null;
    }

    /**
     * Validates move in model
     * @param move move being validated
     * @return Message that move was validated
     */
    public Message validateMove(Move move) {
        return this.board.validateMove(move);
    }

    /**
     * Backs up move in model
     * @return Message that move was backed up
     */
    public Message backupMove() {
        return this.board.backupMove();
    }

    /**
     * Submits turn in model
     * @return Message that turn was submitted
     */
    public Message submitTurn() {
        return this.board.submitTurn();
    }

    /**
     * Adds red player to game
     * @param redPlayer Player
     */
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

    /**
     * sets winner of game
     * @param gameOverMsg String of game over
     * @param whoWon Winner of game
     */
    public void gameOver(String gameOverMsg, Player whoWon){
        this.winner = whoWon;
        this.gameOverMsg = gameOverMsg;
    }

    /**
     * @return String message that says game is over
     */
    public String isGameOver(){
        return gameOverMsg;
    }

    /**
     * @return White Player
     */
    public synchronized Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * @param board add board to game
     */
    public synchronized void addBoard(Board board){
        this.board = board;
    }

    /**
     * @return board of a game
     */
    public synchronized Board getBoard() {
        return this.board;
    }

    /**
     * Checks end conditions of a game
     * to see if a game is over
     */
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
                if(space.getPiece() == null || space.getPiece().getColor() != activeColor){
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
