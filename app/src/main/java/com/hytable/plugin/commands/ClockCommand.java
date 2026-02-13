package com.hytable.plugin.commands;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;

public class ClockCommand extends AbstractWorldCommand {

	public ClockCommand(@Nonnull String name, @Nonnull String description) {
		super(name, description);

		// Add midday subcommand
		this.addSubCommand(new MiddayCommand());
		// Add midnight subcommand
		this.addSubCommand(new MidnightCommand());
		// Add set subcommand
		this.addSubCommand(new SetClockCommand());
	}

	@Override
	protected void execute(
			@Nonnull CommandContext ctx,
			@Nonnull World world,
			@Nonnull Store<EntityStore> store) {
		// Get the time resource
		WorldTimeResource worldTimeResource = store.getResource(WorldTimeResource.getResourceType());

		// Get the exact time with date etc
		LocalDateTime localDateTime = worldTimeResource.getGameDateTime();

		// Get hours
		int localHour = localDateTime.getHour();

		// Get minutes
		int localMinute = localDateTime.getMinute();

		// Convert time to string
		String timeString = String.format("%dh%02d", localHour, localMinute);

		// Send time to player
		ctx.sendMessage(Message.raw("It is " + timeString));
	}

	private static class MiddayCommand extends AbstractWorldCommand {

		public MiddayCommand() {
			super("midday", "Change time to midday");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {
			// Get the time resource
			WorldTimeResource worldTimeResource = store.getResource(WorldTimeResource.getResourceType());

			// Change time to noon
			worldTimeResource.setDayTime(0.5, world, store);

			// Inform the player of the new time
			ctx.sendMessage(Message.raw("It is now noon"));

		}
	}

	private static class MidnightCommand extends AbstractWorldCommand {

		public MidnightCommand() {
			super("midnight", "Change time to midnight");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {
			// Get the time resource
			WorldTimeResource worldTimeResource = store.getResource(WorldTimeResource.getResourceType());

			// Change time to midnight
			worldTimeResource.setDayTime(0, world, store);

			// Inform the player of the new time
			ctx.sendMessage(Message.raw("It is now midnight"));

		}
	}

	private static class SetClockCommand extends AbstractWorldCommand {

		private final RequiredArg<String> timeArg = this.withRequiredArg("time", "The time in format HH:MM", ArgTypes.STRING);

		public SetClockCommand() {
			super("set", "Set the time");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {

			// Get the argument
			String timeInput = this.timeArg.get(ctx);
			
			// Split into hours and minutes
			String[] parts = timeInput.split(":");
			
			if (parts.length != 2) {
				ctx.sendMessage(Message.raw("ERROR --> Format: HH:MM"));
				return;
			}

			int hours = Integer.parseInt(parts[0]);
			int minutes = Integer.parseInt(parts[1]);
			
			if (hours < 0 || hours > 23){
				ctx.sendMessage(Message.raw("ERROR --> Hours must be between 0 and 23"));
				return;
			}

			if (minutes < 0 || minutes > 59){
				ctx.sendMessage(Message.raw("ERROR --> Minutes must be between 0 and 59"));
				return;
			}

			double setDayTime = (hours + minutes / 60.0) / 24;

			WorldTimeResource worldTimeResource = store.getResource(WorldTimeResource.getResourceType());
			
			worldTimeResource.setDayTime(setDayTime, world, store);

			ctx.sendMessage(Message.raw("Time changed to " + String.format("%dh%02d", hours, minutes)));
		}
	}
}