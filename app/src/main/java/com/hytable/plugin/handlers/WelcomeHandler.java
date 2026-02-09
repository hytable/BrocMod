package com.hytable.plugin.handlers;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.util.EventTitleUtil;


public class WelcomeHandler {
    public static void onPlayerJoin(PlayerConnectEvent event) {
        
		//Récupérer la ref du joueur
		PlayerRef PlayerRef = event.getPlayerRef();

		//Lire le nom
		String PlayerName = PlayerRef.getUsername();

		EventTitleUtil.showEventTitleToPlayer(
			PlayerRef,
			Message.raw("Bienvenue !!!"),
			Message.raw("Salut " + PlayerName + " !!!"),
			true);
    }
}
