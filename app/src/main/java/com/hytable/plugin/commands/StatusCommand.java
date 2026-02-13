package com.hytable.plugin.commands;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class StatusCommand extends AbstractPlayerCommand{

	public StatusCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
		super(name, description, requiresConfirmation);
	}

	@Override
	protected void execute(
			@Nonnull CommandContext ctx,
			@Nonnull Store<EntityStore> arg1,
			@Nonnull Ref<EntityStore> arg2, 
			@Nonnull PlayerRef PlayerRef, 
			@Nonnull World World) 
	{
		// Get player name
		String playerName = PlayerRef.getUsername();

		// Get world name
		String worldName = World.getName();

		// Get the store
		Store<EntityStore> store = arg2.getStore();
		
		// Get player stats
		ComponentType<EntityStore, EntityStatMap> componentType = EntityStatMap.getComponentType();

		String statusMessage;
		if (componentType != null) {
    EntityStatMap statMap = (EntityStatMap) store.getComponent(arg2, componentType);
    
    if (statMap != null) {
        // Get health and stamina values  
        EntityStatValue ESVPlayerHP = statMap.get(DefaultEntityStatTypes.getHealth());
        EntityStatValue ESVPlayerStamina = statMap.get(DefaultEntityStatTypes.getStamina());
        
        if (ESVPlayerHP != null && ESVPlayerStamina != null) { // ESV = Entity Stat Value
            Float playerHP = ESVPlayerHP.get();
            Float playerStamina = ESVPlayerStamina.asPercentage() * 100;

            // Create the message
            statusMessage = "Player Status:" +
                            "\n===========" +
                            "\nUsername: " + playerName + 
                            "\nWorld: " + worldName +
                            "\nHP: " + playerHP +
                            "\nStamina: " + playerStamina + "%";
        } else {
            statusMessage = "Health/stamina statistics unavailable";
        }
    } else {
        statusMessage = "Unable to retrieve stats";
    }
} else {
    statusMessage = "Component type not available";
}

// Send message
ctx.sendMessage(Message.raw(statusMessage));			
	}
}
