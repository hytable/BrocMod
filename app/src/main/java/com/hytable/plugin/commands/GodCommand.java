package com.hytable.plugin.commands;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

//Player list with god mode on
public class GodCommand extends AbstractPlayerCommand {

	private static final Set<String> GOD_PLAYERS = new HashSet<>();
	public static boolean isGod(String username) {
		return GOD_PLAYERS.contains(username);
	}

	public GodCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
		super(name, description, requiresConfirmation);
	}

	@Override
	protected void execute(
			@Nonnull CommandContext ctx,
			@Nonnull Store<EntityStore> store,
			@Nonnull Ref<EntityStore> ref,
			@Nonnull PlayerRef playerRef,
			@Nonnull World world) {

		//toggle condition
		String username = playerRef.getUsername();
		boolean enabled = GOD_PLAYERS.contains(username);

		if (enabled) {
			GOD_PLAYERS.remove(username);
			ctx.sendMessage(Message.raw("God Mode disabled"));
		} 
		else {
			GOD_PLAYERS.add(username);
			ctx.sendMessage(Message.raw("God Mode enabled"));
		}
	}
}