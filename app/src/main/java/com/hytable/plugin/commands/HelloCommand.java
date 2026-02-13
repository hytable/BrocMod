package com.hytable.plugin.commands;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;

public class HelloCommand extends AbstractPlayerCommand {

	public HelloCommand(@Nonnull String name,@Nonnull String description, boolean requiresConfirmation) {
		super(name, description, requiresConfirmation);
		
	}

	@Override
	protected void execute(
			@Nonnull CommandContext arg0,
			@Nonnull Store<EntityStore> arg1,
			@Nonnull Ref<EntityStore> arg2, 
			@Nonnull PlayerRef arg3, 
			@Nonnull World arg4) 
	{
		// Type /hello to execute

		EventTitleUtil.showEventTitleToPlayer(
			arg3,
			Message.raw("Welcome adventurer!"), 
			Message.raw("Have fun!"), 
			true
		);		
	}

}
