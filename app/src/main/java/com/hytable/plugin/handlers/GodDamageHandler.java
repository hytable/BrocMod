package com.hytable.plugin.handlers;

import com.hypixel.hytale.component.Ref;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.SystemGroup;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageEventSystem;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hytable.plugin.commands.GodCommand;

// Damage event listener (ECS system). Runs whenever a Damage event is invoked on a player
public class GodDamageHandler extends DamageEventSystem {

	@Nullable
	@Override
	public SystemGroup<EntityStore> getGroup() {
		// Ensure we run in the "filter damage" stage (before damage is applied)
		return DamageModule.get().getFilterDamageGroup();
	}

	@Nonnull
	@Override
	public Query<EntityStore> getQuery() {
		return Player.getComponentType();
	}

	@Override
	public void handle(
			int index,
			@Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
			@Nonnull Store<EntityStore> store,
			@Nonnull CommandBuffer<EntityStore> commandBuffer,
			@Nonnull Damage damage) {

		// Get a reference to the damaged entity (the victim)
		Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);

		// Try to get the PlayerRef component
		PlayerRef playerRef = commandBuffer.getComponent(ref, PlayerRef.getComponentType());
		if (playerRef == null) {
			return;
		}

		String username = playerRef.getUsername();

		// If godmode is enabled for this player, cancel the damage event
		if (GodCommand.isGod(username)) {
			damage.setCancelled(true);
		}
	}
}
