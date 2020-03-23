<#if message??>
  <div id="message" class="${message.type}">${message.text}</div>

  <div id="num_players" class="${num_players.type}">${num_players.text}</div>
<#else>
  <div id="message" class="INFO" style="display:none">
    <!-- keep here for client-side messages -->
  </div>
</#if>
