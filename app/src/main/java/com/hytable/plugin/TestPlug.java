package com.hytable.plugin;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

public class TestPlug extends JavaPlugin {

	public TestPlug(JavaPluginInit init) {
		super(init);
		//TODO Auto-generated constructor stub
	}

	@Override
	protected void setup() {
		super.setup();
		this.getCommandRegistry().registerCommand(new HelloCommand("hello","An exemple command", false));
	}
}
