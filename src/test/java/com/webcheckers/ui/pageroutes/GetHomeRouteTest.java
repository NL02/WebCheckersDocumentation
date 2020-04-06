package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.model.Player;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.WebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The unit test suite for the GetHomeRoute
 *
 * @author Nelson Liang nl3514
 */
@Tag("UI-tier")
public class GetHomeRouteTest {

    /**
     * The component-under-test (CuT)
     */
    private GetHomeRoute CuT;

    // mock objects
    private PlayerLobby lobby;
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;
    private Object Player;
    private ArrayList<Player> waitingPlayers;


    @BeforeEach
    public void testSetup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        Player player = mock(Player.class);
//        when(lobby.getWaitingPlayer()).thenReturn(waitingPlayers);
        lobby = mock(PlayerLobby.class);
        engine = mock(TemplateEngine.class);

        CuT = new GetHomeRoute(lobby, engine);
    }

    /**
     * Test that CuT shows the Home view when the session is brand new.
     */
    @Test
    public void new_session(){
        // To analyze what the Route created in the View-Model map you need
        // to be able to extract the argument to the TemplateEngine.render method.
        // Mock up the 'render' method by supplying a Mockito 'Answer' object
        // that captures the ModelAndView data passed to the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Invoke the test
        CuT.handle(request, response);

        // Analyze the results:
        // * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        // * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR,GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_USER_ATTR, session.attribute("currentUser"));
        testHelper.assertViewModelAttribute(GetHomeRoute.NEW_PLAYER_ATTR, Boolean.TRUE);
        // * test view name
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
        // * verify that a player service object and the session timeout watchdog are stored
        // * in the session
        verify(session).attribute(eq(GetHomeRoute.PLAYERSERVICES_KEY), any(PlayerServices.class));
        verify(session).attribute(eq(GetHomeRoute.TIMEOUT_SESSION_KEY), any(SessionTimeoutWatchdog.class));
    }

    /**
     * Test that CuT redirects to the Game view when a PlayerServices object exists.
     */
    @Test
    public void old_session() {
        // Arrange the test scenario: There is an existing session
        when(session.attribute(GetHomeRoute.PLAYERSERVICES_KEY)).thenReturn(lobby.newPlayerServices());
        // Invoke the test
        assertNotNull(CuT.handle(request,response));

        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        } catch (HaltException e){
            // expected
        }
        // Analyze the results:

        // * redirect to the Game view
        verify(response).redirect(WebServer.GAME_URL);
    }

    /**
     * Test the handle() method to see if it renders the home page
     */
    @Test
    public void testRender() {
        final TemplateEngineTester engineTester = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).thenAnswer(engineTester.makeAnswer());

        // Render the page
        CuT.handle(request, response);

        // Assert that the view-model is a non-null Map with the proper attributes and view name
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute(GetHomeRoute.TITLE, session.attribute(GetHomeRoute.TITLE_ATTR));
        engineTester.assertViewName(GetHomeRoute.VIEW_NAME);
    }




}
