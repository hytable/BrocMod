package com.hytable.plugin.commands;

import javax.annotation.Nonnull;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.MovementSettings;
import com.hypixel.hytale.protocol.SavedMovementStates;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class FlyCommand extends AbstractPlayerCommand {

	public FlyCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
		super(name, description, requiresConfirmation);
		this.addSubCommand(new TurboCommand());
	}

	@Override
	protected void execute(
			@Nonnull CommandContext ctx,
			@Nonnull Store<EntityStore> store,
			@Nonnull Ref<EntityStore> arg2,
			@Nonnull PlayerRef playerRef,
			@Nonnull World world) {
		// Get the Hytale component that stores the player's movement state
		MovementStatesComponent movementStatesComponent = store.getComponent(
				arg2,
				MovementStatesComponent.getComponentType()
		);

		// Read the current flying state (true = flying, false = not flying)
		boolean isFlying = movementStatesComponent.getMovementStates().flying;

		// Toggle the state (ON/OFF)
		boolean newFlying = !isFlying;

		// Get the Player component to apply the new state server/client side
		Player player = store.getComponent(arg2, Player.getComponentType());
		// Reset fly speed to default if turbo was enabled
		MovementManager movementManager = store.getComponent(arg2, MovementManager.getComponentType());
		if (movementManager != null && movementManager.getSettings() != null && movementManager.getDefaultSettings() != null) {
			MovementSettings settings = movementManager.getSettings();
			MovementSettings defaults = movementManager.getDefaultSettings();
			settings.horizontalFlySpeed = defaults.horizontalFlySpeed;
			settings.verticalFlySpeed = defaults.verticalFlySpeed;
			movementManager.update(playerRef.getPacketHandler());
		}

		// Apply the new flying state to the player
		player.applyMovementStates(
				arg2,
				new SavedMovementStates(newFlying),
				movementStatesComponent.getMovementStates(),
				store
		);

		// Inform the player
		if (newFlying) {
			ctx.sendMessage(Message.raw("Fly enabled"));
		} else {
			ctx.sendMessage(Message.raw("Fly disabled"));
		}
	}

	private class TurboCommand extends AbstractPlayerCommand {
		public TurboCommand() {
			super("turbo", "Toggle fly turbo (3x speed)", false);
		}

		@Override
		protected void execute(
				@Nonnull CommandContext ctx,
				@Nonnull Store<EntityStore> store,
				@Nonnull Ref<EntityStore> arg2,
				@Nonnull PlayerRef playerRef,
				@Nonnull World world) {
			// Get movement manager to read and update movement settings
			MovementManager movementManager = store.getComponent(arg2, MovementManager.getComponentType());
			if (movementManager == null) {
				ctx.sendMessage(Message.raw("Movement manager not available"));
				return;
			}

			MovementSettings settings = movementManager.getSettings();
			MovementSettings defaults = movementManager.getDefaultSettings();
			if (settings == null || defaults == null) {
				ctx.sendMessage(Message.raw("Movement settings not available"));
				return;
			}

			// Detect if turbo is already enabled (speed close to 3x default)
			boolean turboEnabled = settings.horizontalFlySpeed >= defaults.horizontalFlySpeed * 2.9f;

			if (turboEnabled) {
				// Restore default fly speeds
				settings.horizontalFlySpeed = defaults.horizontalFlySpeed;
				settings.verticalFlySpeed = defaults.verticalFlySpeed;
				ctx.sendMessage(Message.raw("Fly turbo disabled"));
			} else {
				// Apply 3x fly speeds
				settings.horizontalFlySpeed = defaults.horizontalFlySpeed * 3.0f;
				settings.verticalFlySpeed = defaults.verticalFlySpeed * 3.0f;
				ctx.sendMessage(Message.raw("Fly turbo enabled"));
			}

			// Push the updated movement settings to the client
			movementManager.update(playerRef.getPacketHandler());
		}
	}
}
