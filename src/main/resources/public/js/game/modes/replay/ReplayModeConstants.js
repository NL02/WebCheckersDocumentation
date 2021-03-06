/**
 * This module exports a map of constants used in the REPLAY mode.
 */
define(function(){
    'use strict';

    /**
     * This module is a map of constant symbols to their names.
     * Used in methods to change GameView Replay mode states.
     */
    return {

      STARTING_REPLAY_MODE: 'Starting Replay Mode'
      ,WAITING_FOR_USER_ACTION: 'Waiting for the User Action'
      ,WAITING_FOR_SERVER_RESPONSE: 'Waiting for Server Response'

      //
      // Buttons
      //

      ,NEXT_BUTTON_ID: 'nextBtn'
      ,PREVIOUS_BUTTON_ID: 'previousBtn'
      ,EXIT_BUTTON_ID: 'exitBtn'

      //
      // Options
      //

      ,HAS_NEXT_OPTION: 'hasNext'
      ,HAS_PREVIOUS_OPTION: 'hasPrevious'

    };
});
