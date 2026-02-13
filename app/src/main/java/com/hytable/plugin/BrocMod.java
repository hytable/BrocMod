package com.hytable.plugin;

import javax.annotation.Nonnull;

import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hytable.plugin.commands.HelloCommand;
import com.hytable.plugin.commands.StatusCommand;
import com.hytable.plugin.commands.ClockCommand;
import com.hytable.plugin.handlers.WelcomeHandler;
import com.hytable.plugin.commands.SkyCommand;

public class BrocMod extends JavaPlugin {

	public BrocMod(@Nonnull JavaPluginInit init) {
		super(init);
	}

	@Override
	protected void setup() {
		super.setup();

		this.getCommandRegistry().registerCommand(
			new HelloCommand("hello", "An example command", false));

		this.getCommandRegistry().registerCommand(
			new StatusCommand("status", "Display player status", false));

		this.getCommandRegistry().registerCommand(
			new ClockCommand("clock", "Manage world time")); 
		this.getCommandRegistry().registerCommand(
			new SkyCommand("sky", "Manage sky and weather"));

		this.getEventRegistry().register(PlayerConnectEvent.class, event -> {
			WelcomeHandler.onPlayerJoin(event);
		});
	}
}
