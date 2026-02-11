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

		// Ajoute la sous-commande midday
		this.addSubCommand(new MiddayCommand());
		// Ajoute la sous-commande midnight
		this.addSubCommand(new MidnightCommand());
		// Ajoute la sous-commande set
		this.addSubCommand(new SetCommand());
	}

	@Override
	protected void execute(
			@Nonnull CommandContext ctx,
			@Nonnull World world,
			@Nonnull Store<EntityStore> store) {
		// Récupérer la ressource de temps
		WorldTimeResource worldTimeResource = store.getResource(WorldTimeResource.getResourceType());

		// Récupérer l'heure exact avec date etc
		LocalDateTime localDateTime = worldTimeResource.getGameDateTime();

		// Récupérer les heures
		int localHour = localDateTime.getHour();

		// Récupérer les minutes
		int localMinute = localDateTime.getMinute();

		// Transformer l'heure en string
		String timeString = String.format("%dh%02d", localHour, localMinute);

		// Envoyer l'heure au joueur
		ctx.sendMessage(Message.raw("Il est " + timeString));
	}

	private static class MiddayCommand extends AbstractWorldCommand {

		public MiddayCommand() {
			super("midday", "Change hour to midday");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {
			// Récupérer la ressource de temps
			WorldTimeResource worldTimeResource = store.getResource(WorldTimeResource.getResourceType());

			// Changer l'heure à midi
			worldTimeResource.setDayTime(0.5, world, store);

			// informer le joueur de la nouvelle heure
			ctx.sendMessage(Message.raw("Il est midi"));

		}
	}

	private static class MidnightCommand extends AbstractWorldCommand {

		public MidnightCommand() {
			super("midnight", "Change hour to midnight");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {
			// Récupérer la ressource de temps
			WorldTimeResource worldTimeResource = store.getResource(WorldTimeResource.getResourceType());

			// Changer l'heure à minuit
			worldTimeResource.setDayTime(0, world, store);

			// informer le joueur de la nouvelle heure
			ctx.sendMessage(Message.raw("Il est minuit"));

		}
	}

	private static class SetCommand extends AbstractWorldCommand {

		private final RequiredArg<String> timeArg = this.withRequiredArg("time", "The time in format HH:MM", ArgTypes.STRING);

		public SetCommand() {
			super("set", "Set the time");
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull World world,
				@Nonnull Store<EntityStore> store) {

			// Récupérer l'argument
			String timeInput = this.timeArg.get(ctx);
			
			//Découper en heure et minute avec split
			String[] parts = timeInput.split(":");
			
			if (parts.length != 2) {
				ctx.sendMessage(Message.raw("ERROR --> HH:MM"));
				return;
			}

			int hours = Integer.parseInt(parts[0]);
			int minutes = Integer.parseInt(parts[1]);
			
			if (hours < 0 || hours > 23){
				ctx.sendMessage(Message.raw("ERROR --> Between 0 and 23"));
				return;
			}

			else if (minutes < 0 || minutes > 59){
				ctx.sendMessage(Message.raw("ERROR --> Between 0 and 59"));
				return;
			}

			double setDayTime = (hours + minutes / 60.0) / 24;

			WorldTimeResource worldTimeResource = store.getResource(WorldTimeResource.getResourceType());
			
			worldTimeResource.setDayTime(setDayTime, world, store);

			ctx.sendMessage(Message.raw("L'heure a été changée à " + String.format("%dh%02d", hours, minutes )));
		}
	}
}