package com.hytable.plugin.commands;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
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
	}

	@Override
	protected void execute(
			@Nonnull CommandContext ctx, 
			@Nonnull World world, 
			@Nonnull Store<EntityStore> store) 
	{	
		//Récupérer la ressource de temps
		WorldTimeResource worldTimeResource = store.getResource(WorldTimeResource.getResourceType());
		
		//Récupérer l'heure exact avec date etc
		LocalDateTime localDateTime = worldTimeResource.getGameDateTime();
		
		//Récupérer les heures
		int localHour = localDateTime.getHour();

		//Récupérer les minutes
		int localMinute = localDateTime.getMinute();

		//Transformer l'heure en string
		String timeString = String.format("%dh%02d", localHour, localMinute);

		//Envoyer l'heure au joueur
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
				@Nonnull Store<EntityStore> store)
		{
			//Récupérer la ressource de temps
			WorldTimeResource worldTimeResource = store.getResource(WorldTimeResource.getResourceType());

			//Changer l'heure à midi
			worldTimeResource.setDayTime(0.5, world, store);

			//informer le joueur de la nouvelle heure
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
				@Nonnull Store<EntityStore> store)
		{
			//Récupérer la ressource de temps
			WorldTimeResource worldTimeResource = store.getResource(WorldTimeResource.getResourceType());

			//Changer l'heure à minuit
			worldTimeResource.setDayTime(0, world, store);

			//informer le joueur de la nouvelle heure
			ctx.sendMessage(Message.raw("Il est minuit"));

		}
	}
}