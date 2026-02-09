package com.hytable.plugin;

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
		//TODO Auto-generated constructor stub
	}

	@Override
	protected void execute(
			@Nonnull CommandContext arg0,
			@Nonnull Store<EntityStore> arg1,
			@Nonnull Ref<EntityStore> arg2, 
			@Nonnull PlayerRef arg3, 
			@Nonnull World arg4) 
	{
		// TODO Auto-generated method stub

		EventTitleUtil.showEventTitleToPlayer(
			arg3,
			Message.raw("Hello world!"), 
			Message.raw("Ta race Neras"), 
			true
		);		
	}

}
