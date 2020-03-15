package com.webcheckers.ui.pageroutes;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.WebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

/**
 * The unit test suite for the PostSignOutTester
 *
 * @author Rayna Mishra rvm8343
 */
@Tag("UI-tier")
public class PostSignOutRouteTester {

    /**
     * The component-under-test (CuT)
     */
    private PostSignOutRoute CuT;

    // mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby lobby;
    private Response response;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup(){
       request = mock(Request.class);
       session = mock(Session.class);
       when(request.session()).thenReturn(session);
       response = mock(Response.class);
       lobby = mock(PlayerLobby.class);
       engine = mock(TemplateEngine.class);

       CuT = new PostSignOutRoute(lobby, engine);
    }

    /**
     * Test ability to
     */
    @Test
    public void test_new_sign_out(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());

        // Invoke test
        CuT.handle(request, response);

        // Analyze results:
        //      * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //      * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(PostSignOutRoute.CURRENT_USER_ATTR, session.attribute("currentUser"));
        //      * test view name
        // todo: testHelper.assertViewName(PostSignOutRoute.VIEWNAME);
        //  also, do we need a timeout session key here?
    }

    /**
     * Test ability to remove a player from the online players list given a player that was online
     */
    @Test
    public void removePlayerWasOnline(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test ability to remove a player from the online players list given a player that is
     * not online
     */
    @Test
    public void removePlayerWasNotOnline(){

    }

    /**
     * Test ability to set the current player's status to offline
     */
    @Test
    public void setStatusOFFLINE(){

    }

    /**
     * Test ability to remove the current player from the session attribute
     */
    @Test
    public void removePlayerFromSession(){

    }

    /**
     * Test that CuT redirects to the home page after a player signs out (at the end of the sign
     * out routine)
     */
    @Test
    public void redirectToHome(){
        CuT.handle(request, response);
        verify(response).redirect(WebServer.HOME_URL);
    }
}
