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

	public StatusCommand(@Nonnull String name,@Nonnull String description, boolean requiresConfirmation) {
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
		//Récuperer le nom du joueur
		String playerName = PlayerRef.getUsername();

		//Récupérer le nom du monde
		String worldName = World.getName();

		//Récupérer le store
		Store<EntityStore> store = arg2.getStore();
		
//Récupérer les stats du joueur
ComponentType<EntityStore, EntityStatMap> componentType = EntityStatMap.getComponentType();

String statusMessage;
if (componentType != null) {
    EntityStatMap statMap = (EntityStatMap) store.getComponent(arg2, componentType);
    
    if (statMap != null) {
        // Récupérer les valeurs de vie et stamina  
        EntityStatValue ESVPlayerHP = statMap.get(DefaultEntityStatTypes.getHealth());
        EntityStatValue ESVPlayerStamina = statMap.get(DefaultEntityStatTypes.getStamina());
        
        if (ESVPlayerHP != null && ESVPlayerStamina != null) { //ESV = Entity Stat Value
            Float playerHP = ESVPlayerHP.get();
            Float playerStamina = ESVPlayerStamina.asPercentage() * 100;

            //Créer le message
            statusMessage = "Statut Joueur :" +
                            "\n==========" +
                            "\nPseudo: " + playerName + 
                            "\nMonde: " + worldName +
                            "\nHP: " + playerHP +
                            "\nStamina: " + playerStamina + "%";
        } else {
            statusMessage = "Statistiques de santé/stamina indisponibles";
        }
    } else {
        statusMessage = "Impossible de récupérer les stats";
    }
} else {
    statusMessage = "Type de composant non disponible";
}

// Afficher le message
ctx.sendMessage(Message.raw(statusMessage));			
	}
}
