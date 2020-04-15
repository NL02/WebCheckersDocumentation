---
geometry: margin=1in
---
# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._

## Team Information
* Team name: C-hess
* Team members
  * Wyatt Holcombe
  * Rayna Mishra
  * Ethan Davison
  * May Wu
  * Nelson Liang

## Executive Summary

A web application that allows players to create individual accounts and play checkers online.

### Purpose
Learn more about the model-view-controller architecture as well as enforce software engineering principles.

### Glossary and Acronyms

| Term | Definition |
|------|------------|
| MVC  | Model-View-Controller |
| MVP  | Minimum Viable Product|
| JS   | JavaScript            |
| HTML | HyperText Markup Language |
| CSS  | Cascading Style Sheets |



## Requirements

This section describes the features of the application.

* Log in with a unique name and log out
* Create and enter a game of checkers with another person
* Play checkers according to the American Rules
* Resign
* Spectate a live game
* Playback a previously completed game 

### Definition of MVP

   Log in, log out, resign, and play checkers according to the American rules. 

### MVP Features
> _Provide a list of top-level Epics and/or Stories of the MVP._
* As a player, I want to start a game so that I can play checkers with an opponent.
* As a player, I want to sign out so that I can stop playing. 
* As a player, I want to sign in so that I can play a game of checkers. 
* As a player, I want to resign so that I can play another game. 

### Roadmap of Enhancements
> _Provide a list of top-level features in the order you plan to consider them._
* Log in 
* Log out
* Join a game
* Moving diagonally
* Single Jump
* Multiple Jump 
* Kinging a piece
* Resigning 
* Spectate 
* Playback 



## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](domain-model-placeholder.png)

> _Provide a high-level overview of the domain for this application. You
> can discuss the more important domain entities and their relationship
> to each other._


## Architecture and Design

This section describes the application architecture.
* Maven
* Jetty 
* Ajax
* Spark
* Freemarker
* Java 8 
* JavaScript

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a
browser.  The client-side of the UI is composed of HTML pages with
some minimal CSS for styling the page.  There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.
The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](web-interface-placeholder.png)

> _Provide a summary of the application's user interface.  Describe, from
> the user's perspective, the flow of the pages in the web application._

When the user first opens up the page they see the number of online players and a button
to sign in. After clicking the sign in button, the user is redirected to another page 
and prompted to enter in a valid username. After entering a valid username the player 
is redirected back to the homepage and can start a game by clicking the create game button 
or they can click on the name of other players to join their game. If they decide to enter 
a game they're redirected to the game page. If the user decides to logout, they can click the 
logout button on the navigation bar and are redirected the homepage with the sign in button 
the online player count. 


### UI Tier
> _Provide a summary of the Server-side UI tier of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class structure or object diagrams) with some
> details such as critical attributes and methods._

> _You must also provide any dynamic models, such as statechart and
> sequence diagrams, as is relevant to a particular aspect of the design
> that you are describing.  For example, in WebCheckers you might create
> a sequence diagram of the `POST /validateMove` HTTP request processing
> or you might show a statechart diagram if the Game component uses a
> state machine to manage the game._

> _If a dynamic model, such as a statechart describes a feature that is
> not mostly in this tier and cuts across multiple tiers, you can
> consider placing the narrative description of that feature in a
> separate section for describing significant features. Place this after
> you describe the design of the three tiers._

When the user first enters the web page the GetHomeRoute correctly displays the sign in button
as well as the online player count. After clicking the sign in button, the GetLoginRoute displays a 
text field where the user can enter in a valid username. If they enter an invalid username then, the 
PostLoginRoute handles the error by displaying a message with the appropriate message. If the username
is valid then PostLoginRoute redirects the user back to home. 

When the user successfully logs in and sees the home page they can now see the button "Create Game",
a sign out button and a  list of player names, some of them are clickable. If the user clicks on the 
clickable names then, the GetGameRoute gets called and the user is placed into a game with the previously 
clicked name.
>Include comments here about game option routes, submit turn, resign, etc.

If the user decides that they instead want to click on the "Create Game" button then the GetNewGameRoute
is called and the user now waits for an opponent, and their name appears clickable on the home page. If
the user instead chooses to click the sign out button then the PostSignOutRoute gets called and the 
user is redirected back to the home page with the live player count and the sign in button. 

### Application Tier
> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._

The Player Lobby keeps track of the players in the game as well as the online players, it also has
methods to handle players logging in as well as logging out. The Player keeps track of the player name, 
their online status. 
> Might include arrays of moves. 


### Model Tier
> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._

The model tier includes an instance of the CheckerBoard, the routes for submitting movement,
and the controllers for the WebApplication. 
>Will include more information while working on sprint 2. 

### Design Improvements
> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements. After completion of the Code metrics exercise, you
> will also discuss the resutling metric measurements.  Indicate the
> hot spots the metrics identified in your code base, and your
> suggested design improvements to address those hot spots._

* Improve aesthetics of the game. 
* Message functionality 
* Display statistics
* Add an AI to play against

## Testing
> _This section will provide information about the testing performed
> and the results of the testing._

* All classes will have unit tests
>Will include the statistics later 

### Acceptance Testing
> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

We plan on first focusing the stories that help create the MVP and then plan on working on enhancements. 
One issue during acceptance testing was that one of our .ftl files had incorrect syntax and it set us back. 


### Unit Testing and Code Coverage
> _Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets. If there are any anomalies, discuss
> those._

We plan on writing out the stories first and then unit testing them. The person that writes 
a class will create the unit tests for it. As a team, we're aiming to pass 100% of our 
tests but realistically 90-95% is acceptable. 