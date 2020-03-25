package com.webcheckers.appl;

import com.webcheckers.model.CheckersGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;


/**
 * The unit test suite for the PlayerServices component
 *
 * @author Nelson Liang nl3514
 */
@Tag("Application-tier")
public class PlayerServicesTest {

    /**
     * The component-under-test (CuT)
     */
    private PlayerServices CuT;

    // friendly objects
    private PlayerLobby lobby;

    private CheckersGame game;

    @BeforeEach
    public void testSetup() {
        lobby = mock(PlayerLobby.class);

        // Setup CuT and create the game
        CuT = new PlayerServices(lobby);
    }

    /**
     * Test that you can construct a new Player Service.
     */
    @Test
    public void test_create_service() {
        new PlayerServices(lobby);
    }

    /**
     * Test that two players arn't equal
     */


}
