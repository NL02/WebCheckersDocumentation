<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>
  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

  <#if currentUser??>
    <h2><a href="/newgame">New Game</a></h2>
  </#if>
    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

  <#if currentUser??>
    <#list playerList as player>
        <p><a href="/game" name = "opponent">${player.name}</a></p>
    </#list>
  </#if>




    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
