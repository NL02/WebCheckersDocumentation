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
    <h2><a href="/game">Create Game</a></h2>
  </#if>
    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

  <#if currentUser??>
    <#list playerList as player>
        <p><a href="/game?opponent=${player.name}">${player.name}</a></p>
    </#list>
  </#if>

  </div>

</div>
</body>

</html>
