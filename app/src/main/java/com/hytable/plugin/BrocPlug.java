package com.hytable.plugin;

import javax.annotation.Nonnull;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hytable.plugin.commands.HelloCommand;
import com.hytable.plugin.commands.StatusCommand;

public class BrocPlug extends JavaPlugin {

	public BrocPlug(@Nonnull JavaPluginInit init) {
		super(init);
	}

	@Override
	protected void setup() {
		super.setup();
		this.getCommandRegistry().registerCommand(
			new HelloCommand("hello","An exemple command", false));
		this.getCommandRegistry().registerCommand(
			new StatusCommand("status", "Affiche le statut du joueur", false));
	}
}
