package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the PostSignOutTester
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

    @BeforeEach
    public void testSetup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        lobby = mock(PlayerLobby.class);
        engine = mock(TemplateEngine.class);

        CuT = new GetHomeRoute(lobby, engine);
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

//         Assert that the view-model is a non-null Map with the proper attributes and view name
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute(GetHomeRoute.TITLE, session.attribute(GetHomeRoute.TITLE_ATTR));
        engineTester.assertViewName(GetHomeRoute.VIEW_NAME);
    }




}
