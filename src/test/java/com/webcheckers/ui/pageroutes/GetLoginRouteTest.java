package com.webcheckers.ui.pageroutes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit test suite for the GetLoginRoute handler.
 *
 * @author Wyatt Holcombe
 */
@Tag("UI-Tier")
public class GetLoginRouteTest {

    /**
     * Test message
     */
    private static final Message testMessage = Message.info("test");

    /**
     * HTML tags to look for
     */
    private static final String TITLE_HEAD_TAG = "<title> " + GetLoginRoute.TITLE + "</title>";
    private static final String TITLE_H1_TAG = "<h1>" + GetLoginRoute.TITLE + "</h1>";
    private static final String MESSAGE_TAG = "<div id=\"message\" class=\"" + testMessage.getType()
            + "\">" + testMessage.getText() + "</div>";

    /**
     * Component under test, or CuT
     */
    private GetLoginRoute CuT;

    /**
     * Mock objects
     */
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby lobby;
    private Response response;

    /**
     * Set up new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        lobby = mock(PlayerLobby.class);
        engine = mock(TemplateEngine.class);

        CuT = new GetLoginRoute(lobby, engine);
    }

    /**
     * Test the handle() method to see if it renders the login page properly
     */
    @Test
    public void testRender() throws Exception {
        final TemplateEngineTester engineTester = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).thenAnswer(engineTester.makeAnswer());

        // Render the page
        CuT.handle(request, response);

        // Assert that the view-model is a non-null Map with the proper attributes and view name
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute(GetLoginRoute.TITLE, session.attribute(GetLoginRoute.TITLE_ATTR));
        engineTester.assertViewName(GetLoginRoute.VIEW_NAME);
    }

    /**
     * Test to verify that the HTML page displays the correct attributes without a message
     */
    @Test
    public void testNoMessage() {
        // Set up and render view-model
        final TemplateEngine testEngine = new FreeMarkerEngine();
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetLoginRoute.VIEW_NAME);
        vm.put(GetLoginRoute.TITLE_ATTR, GetLoginRoute.TITLE);

        final String viewHTML = testEngine.render(modelAndView);

        // Analyze results
        assertNotNull(viewHTML);
        assertTrue(viewHTML.contains(TITLE_HEAD_TAG), "Title head tag exists.");
        assertTrue(viewHTML.contains(TITLE_H1_TAG), "Title header tag exists.");
        assertFalse(viewHTML.contains(MESSAGE_TAG), "Message does not exist.");
    }

    /**
     * Test to verify that the HTML page displays the correct attributes with a message
     */
    @Test
    public void testMessage() {
        // Set up and render view-model
        final TemplateEngine testEngine = new FreeMarkerEngine();
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetLoginRoute.VIEW_NAME);
        vm.put("message", testMessage);
        vm.put(GetLoginRoute.TITLE_ATTR, GetLoginRoute.TITLE);

        final String viewHTML = testEngine.render(modelAndView);

        // Analyze results
        assertTrue(viewHTML.contains(TITLE_HEAD_TAG), "Title head tag exists.");
        assertTrue(viewHTML.contains(TITLE_H1_TAG), "Title header tag exists.");
        assertTrue(viewHTML.contains(MESSAGE_TAG), "Message tag exists.");
    }

}
