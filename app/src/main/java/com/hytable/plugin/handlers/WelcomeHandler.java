package com.hytable.plugin.handlers;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.util.EventTitleUtil;


public class WelcomeHandler {
    public static void onPlayerJoin(PlayerConnectEvent event) {
        
		// Get player reference
		PlayerRef PlayerRef = event.getPlayerRef();

		// Get player name
		String PlayerName = PlayerRef.getUsername();

		EventTitleUtil.showEventTitleToPlayer(
			PlayerRef,
			Message.raw("Welcome!!!"),
			Message.raw("Hi " + PlayerName + "!!!"),
			true);
    }
}
