package com.hytable.plugin.commands;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;

public class SkyCommand extends AbstractWorldCommand {
	public SkyCommand(String name, String description) {
		super(name, description);

		// Enregistrement des différentes options météo
		this.addSubCommand(new ClearCommand());
		this.addSubCommand(new RainCommand());
		this.addSubCommand(new StormCommand());
	}

	@Override
	protected void execute(
			@Nonnull CommandContext ctx,
			@Nonnull World world,
			@Nonnull Store<EntityStore> store) {

		// Message d'aide si aucune option n'est saisie
		ctx.sendMessage(Message.raw("Usage: clear/rain/storm"));
	}

	// Conteneur pour retourner plusieurs informations d'un coup
	private static class SkyData {
		public WeatherResource resource;
		public String currentId;
	}

	// Récupère à la fois l'outil de gestion et le nom de la météo actuelle
	protected static SkyData SkyWrapper(Store<EntityStore> store) {
		WeatherResource weatherResource = store.getResource(WeatherResource.getResourceType());
		int index = weatherResource.getForcedWeatherIndex();

		// Sécurisation : vérifie si une météo est active
		String currentMeteo = "none";
		if (index != 0) {
			Weather currentAsset = Weather.getAssetMap().getAsset(index);
			if (currentAsset != null) {
				currentMeteo = currentAsset.getId();
			}
		}

		SkyData data = new SkyData();
		data.resource = weatherResource;
		data.currentId = currentMeteo;

		return data;
	}

	// Change la météo et sauvegarde dans la config du monde
	protected static void applyWeather(World world, Store<EntityStore> store, String weatherId) {
		WeatherResource weatherResource = store.getResource(WeatherResource.getResourceType());
		weatherResource.setForcedWeather(weatherId);
	}

	private class ClearCommand extends AbstractWorldCommand {
		public ClearCommand() {
			super("clear", "Soleil");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {

			SkyData weather = SkyWrapper(store);

			if (weather.currentId.equals("Skylands_Sunny")) {
				ctx.sendMessage(Message.raw("Le soleil brille déjà !"));
				return;
			}

			applyWeather(world, store, "Skylands_Sunny");
			ctx.sendMessage(Message.raw("Le ciel se dégage ..."));

		}
	}

	private class RainCommand extends AbstractWorldCommand {
		public RainCommand() {
			super("rain", "Pluie");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {

			SkyData weather = SkyWrapper(store);

			if (weather.currentId.equals("Zone4_Wastes_Rain")) {
				ctx.sendMessage(Message.raw("La pluie est déjà là !"));
				return;
			}

			applyWeather(world, store, "Zone4_Wastes_Rain");
			ctx.sendMessage(Message.raw("La pluie commence à tomber ..."));

		}
	}

	private class StormCommand extends AbstractWorldCommand {
		public StormCommand() {
			super("storm", "Tempête");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {

			SkyData weather = SkyWrapper(store);

			if (weather.currentId.equals("Zone1_Storm")) {
				ctx.sendMessage(Message.raw("Tu ne vois pas la tempête ?!"));
				return;
			}

			applyWeather(world, store, "Zone1_Storm");
			ctx.sendMessage(Message.raw("Le ciel se noircit rapidement ..."));

		}
	}
}
