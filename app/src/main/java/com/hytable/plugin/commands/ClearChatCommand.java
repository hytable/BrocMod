package com.hytable.plugin.commands;

import javax.annotation.Nonnull;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class ClearChatCommand extends AbstractPlayerCommand {

	public ClearChatCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
		super(name, description, requiresConfirmation);
	}

	@Override
	protected void execute(
			@Nonnull CommandContext ctx,
			@Nonnull Store<EntityStore> store,
			@Nonnull Ref<EntityStore> ref,
			@Nonnull PlayerRef playerRef,
			@Nonnull World world) {

		for (int i = 0; i < 100; i++) {
			ctx.sendMessage(Message.raw(" "));
		}
	}
}