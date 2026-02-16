package com.hytable.plugin;

import javax.annotation.Nonnull;

import com.hytable.plugin.commands.ClearChatCommand;
import com.hytable.plugin.commands.ClockCommand;
import com.hytable.plugin.commands.FlyCommand;
import com.hytable.plugin.commands.GodCommand;
import com.hytable.plugin.commands.HelloCommand;
import com.hytable.plugin.commands.SkyCommand;
import com.hytable.plugin.commands.StatusCommand;
import com.hytable.plugin.handlers.GodDamageHandler;
import com.hytable.plugin.handlers.WelcomeHandler;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

public class BrocMod extends JavaPlugin {

	private static BrocMod instance;

	public static BrocMod getInstance() {
		return instance;
	}

	public BrocMod(@Nonnull JavaPluginInit init) {
		super(init);
		instance = this;
	}

	@Override
	protected void setup() {
		super.setup();

		// Commands
		this.getCommandRegistry().registerCommand(
				new HelloCommand("hello", "An example command", false));
		this.getCommandRegistry().registerCommand(
				new StatusCommand("status", "Display player status", false));
		this.getCommandRegistry().registerCommand(
				new ClockCommand("clock", "Manage world time"));
		this.getCommandRegistry().registerCommand(
				new SkyCommand("sky", "Manage sky and weather"));
		this.getCommandRegistry().registerCommand(
				new FlyCommand("fly", "Allow the player to set fly on/off", false));
		this.getCommandRegistry().registerCommand(
				new ClearChatCommand("clearchat", "Clear chat", false));
		this.getCommandRegistry().registerCommand(
				new GodCommand("god", "GodMode", false));

		// Entity Component System(ECS) systems / handlers
		this.getEntityStoreRegistry().registerSystem(new GodDamageHandler());

		// Events
		this.getEventRegistry().register(PlayerConnectEvent.class, event -> {
			WelcomeHandler.onPlayerJoin(event);
		});
	}
}
