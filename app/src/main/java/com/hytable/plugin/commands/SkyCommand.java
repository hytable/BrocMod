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
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;

public class SkyCommand extends AbstractWorldCommand {
	public SkyCommand(String name, String description) {
		super(name, description);

		// Registration of different weather options
		this.addSubCommand(new ClearCommand());
		this.addSubCommand(new RainCommand());
		this.addSubCommand(new StormCommand());
		this.addSubCommand(new SetCommand());
		this.addSubCommand(new CurrentCommand());
		this.addSubCommand(new IdCommand());
	}

	@Override
	protected void execute(
			@Nonnull CommandContext ctx,
			@Nonnull World world,
			@Nonnull Store<EntityStore> store) {

		// Help message if no option is specified
		ctx.sendMessage(Message.raw("Usage: clear/rain/storm/set/current/id"));
	}

	// Container to return multiple values at once
	private static class SkyData {
		public WeatherResource resource;
		public String currentId;
	}

	// Get both the management tool and the current weather name
	protected static SkyData SkyWrapper(Store<EntityStore> store) {
		WeatherResource weatherResource = store.getResource(WeatherResource.getResourceType());
		int index = weatherResource.getForcedWeatherIndex();

		// Security: check if a weather is active
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

	// Change the weather
	protected static void applyWeather(World world, Store<EntityStore> store, String weatherId) {
		WeatherResource weatherResource = store.getResource(WeatherResource.getResourceType());
		weatherResource.setForcedWeather(weatherId);
	}

	private class ClearCommand extends AbstractWorldCommand {
		public ClearCommand() {
			super("clear", "Clear sky");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {

			SkyData weather = SkyWrapper(store);

			if (weather.currentId.equals("Skylands_Sunny")) {
				ctx.sendMessage(Message.raw("The sun is already shining!"));
				return;
			}

			applyWeather(world, store, "Skylands_Sunny");
			ctx.sendMessage(Message.raw("The sky is clearing..."));

		}
	}

	private class RainCommand extends AbstractWorldCommand {
		public RainCommand() {
			super("rain", "Rain");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {

			SkyData weather = SkyWrapper(store);

			if (weather.currentId.equals("Zone4_Wastes_Rain")) {
				ctx.sendMessage(Message.raw("Rain is already falling!"));
				return;
			}

			applyWeather(world, store, "Zone4_Wastes_Rain");
			ctx.sendMessage(Message.raw("The rain is starting to fall..."));

		}
	}

	private class StormCommand extends AbstractWorldCommand {
		public StormCommand() {
			super("storm", "Storm");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {

			SkyData weather = SkyWrapper(store);

			if (weather.currentId.equals("Zone1_Storm")) {
				ctx.sendMessage(Message.raw("Can't you see the storm?!"));
				return;
			}

			applyWeather(world, store, "Zone1_Storm");
			ctx.sendMessage(Message.raw("The sky is darkening rapidly..."));

		}
	}

	private class CurrentCommand extends AbstractWorldCommand {
		public CurrentCommand() {
			super("current", "Display current weather ID");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {

			SkyData weather = SkyWrapper(store);

			if (weather.currentId.equals("none")) {
				ctx.sendMessage(Message.raw("No forced weather currently active."));
			} else {
				ctx.sendMessage(Message.raw("Current weather ID: " + weather.currentId));
			}
		}
	}

	private class SetCommand extends AbstractWorldCommand {
		private final RequiredArg<String> weatherIdArg = this.withRequiredArg("weather_id", "The weather ID to apply", ArgTypes.STRING);

		public SetCommand() {
			super("set", "Apply a weather by ID");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {

			// Get the weather argument
			String weatherId = weatherIdArg.get(ctx);
			
			applyWeather(world, store, weatherId);
			ctx.sendMessage(Message.raw("Weather applied: " + weatherId));
		}
	}

	private class IdCommand extends AbstractWorldCommand {
		public IdCommand() {
			super("id", "Browse weather IDs by category");
			this.addSubCommand(new IdClearCommand());
			this.addSubCommand(new IdRainCommand());
			this.addSubCommand(new IdStormCommand());
			this.addSubCommand(new IdMistCommand());
			this.addSubCommand(new IdCloudyCommand());
			this.addSubCommand(new IdSpecialCommand());
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {
			// Show available categories
			ctx.sendMessage(Message.raw("=== Weather Categories ==="));
			ctx.sendMessage(Message.raw("• /sky id clear - Sunny weather"));
			ctx.sendMessage(Message.raw("• /sky id rain - Rainy weather"));
			ctx.sendMessage(Message.raw("• /sky id storm - Storms"));
			ctx.sendMessage(Message.raw("• /sky id mist - Fog and mist"));
			ctx.sendMessage(Message.raw("• /sky id cloudy - Cloudy weather"));
			ctx.sendMessage(Message.raw("• /sky id special - Special atmospheres"));
		}
	}

	private class IdClearCommand extends AbstractWorldCommand {
		public IdClearCommand() {
			super("clear", "List sunny weather IDs");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {
			ctx.sendMessage(Message.raw("=== Sunny / Good Weather ==="));
			ctx.sendMessage(Message.raw("1. Skylands_Light"));
			ctx.sendMessage(Message.raw("2. Zone4_Light"));
			ctx.sendMessage(Message.raw("3. Zone2_Blazing_Light"));
			ctx.sendMessage(Message.raw("4. Skylands_Mushroom_Forest_Cloudy"));
			ctx.sendMessage(Message.raw("5. Zone2_Cloudy_Light"));
			ctx.sendMessage(Message.raw("6. Zone3_Cloudy_Light"));
			ctx.sendMessage(Message.raw("7. Zone3_Cave_Shallow"));
			ctx.sendMessage(Message.raw("8. Cave_Shallow"));
			ctx.sendMessage(Message.raw("9. Zone1_Sunny_Fireflies"));
			ctx.sendMessage(Message.raw("10. Zone1_Gully"));
			ctx.sendMessage(Message.raw("11. Zone1_Azurewood_Fireflies"));
			ctx.sendMessage(Message.raw("12. Skylands_Sunny"));
			ctx.sendMessage(Message.raw("13. Zone1_Swamp"));
			ctx.sendMessage(Message.raw("14. Zone3_Northern_Lights"));
			ctx.sendMessage(Message.raw("15. Zone2_Corrupted_Oasis"));
			ctx.sendMessage(Message.raw("16. Zone2_Story_Dungeon_Interior1"));
		}
	}

	private class IdRainCommand extends AbstractWorldCommand {
		public IdRainCommand() {
			super("rain", "List rainy weather IDs");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {
			ctx.sendMessage(Message.raw("=== Rain ==="));
			ctx.sendMessage(Message.raw("1. Zone3_Cave_Deep"));
			ctx.sendMessage(Message.raw("2. Zone4_Wastes_Rain"));
			ctx.sendMessage(Message.raw("3. Skylands_Rapid_Marsh_Stormy"));
			ctx.sendMessage(Message.raw("4. Dungeon_Cursed_Crypt_Graveyard"));
			ctx.sendMessage(Message.raw("5. Zone4_Caves_Jungles"));
			ctx.sendMessage(Message.raw("6. Zone4_GhostForest_Rain"));
			ctx.sendMessage(Message.raw("7. Zone4_Wastes_Rain_Heavy"));
		}
	}

	private class IdStormCommand extends AbstractWorldCommand {
		public IdStormCommand() {
			super("storm", "List storm weather IDs");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {
			ctx.sendMessage(Message.raw("=== Storm / Thunderstorm ==="));
			ctx.sendMessage(Message.raw("1. Zone2_Thunder_Storm"));
			ctx.sendMessage(Message.raw("2. Zone4_AshWastes_Storm"));
			ctx.sendMessage(Message.raw("3. Zone4_Swamp_Storm"));
			ctx.sendMessage(Message.raw("4. Zone2_Sand_Storm"));
			ctx.sendMessage(Message.raw("5. Zone1_Storm"));
		}
	}

	private class IdMistCommand extends AbstractWorldCommand {
		public IdMistCommand() {
			super("mist", "List fog and mist weather IDs");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {
			ctx.sendMessage(Message.raw("=== Mist / Fog ==="));
			ctx.sendMessage(Message.raw("1. Zone3_Hedera"));
			ctx.sendMessage(Message.raw("2. Dungeon_Cursed_Crypt"));
			ctx.sendMessage(Message.raw("3. Zone4_Forest_Root"));
			ctx.sendMessage(Message.raw("4. Zone4_Geyser"));
			ctx.sendMessage(Message.raw("5. Hedera_Forest"));
			ctx.sendMessage(Message.raw("6. Zone1_Swamp_Foggy"));
			ctx.sendMessage(Message.raw("7. Zone4_GhostForest"));
			ctx.sendMessage(Message.raw("8. Cave_Deep"));
		}
	}

	private class IdCloudyCommand extends AbstractWorldCommand {
		public IdCloudyCommand() {
			super("cloudy", "List cloudy weather IDs");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {
			ctx.sendMessage(Message.raw("=== Cloudy / Overcast ==="));
			ctx.sendMessage(Message.raw("1. Zone4_Wastes"));
			ctx.sendMessage(Message.raw("2. Zone2_Cloudy_Heavy"));
			ctx.sendMessage(Message.raw("3. Zone4_Underground_Jungle"));
			ctx.sendMessage(Message.raw("4. Zone4_Swamp_Gloomy"));
			ctx.sendMessage(Message.raw("5. Skylands_Rapid_Marsh_Cloudy_Medium"));
		}
	}

	private class IdSpecialCommand extends AbstractWorldCommand {
		public IdSpecialCommand() {
			super("special", "List special atmosphere IDs");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {
			ctx.sendMessage(Message.raw("=== Special Atmospheres ==="));
			ctx.sendMessage(Message.raw("1. Poison_Test"));
			ctx.sendMessage(Message.raw("2. Portals_Void_Event_Light"));
			ctx.sendMessage(Message.raw("3. Zone3_Cave_Volcanic"));
			ctx.sendMessage(Message.raw("4. Poisonlands_Red"));
			ctx.sendMessage(Message.raw("5. Void"));
			ctx.sendMessage(Message.raw("6. Zone2_Corrupted_Oasis_Night"));
			ctx.sendMessage(Message.raw("7. Zone4_Underground_Jungle_Pink"));
			ctx.sendMessage(Message.raw("8. Terror_Weather"));
			ctx.sendMessage(Message.raw("9. Zone4_AshWastes"));
		}
	}
}
