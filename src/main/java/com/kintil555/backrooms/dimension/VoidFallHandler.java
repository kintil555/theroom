package com.kintil555.backrooms.dimension;

import com.kintil555.backrooms.Backrooms;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Watches players inside the_backrooms. Once a player falls below the
 * dimension's min_y (into the void under the generated floor), a 1 second
 * (20 tick) timer starts; if they are still below min_y when it expires
 * they are teleported to the overworld at their X/Z, Y=150.
 *
 * The timer resets if the player returns above min_y before it expires,
 * so a brief fall through a hole that gets caught doesn't trigger it.
 */
public final class VoidFallHandler {

    private static final int TELEPORT_DELAY_TICKS = 20; // 1 second
    private static final double RETURN_Y = 150.0;

    private static final Map<UUID, Integer> fallTicks = new HashMap<>();

    private VoidFallHandler() {
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(VoidFallHandler::onTick);
    }

    private static void onTick(MinecraftServer server) {
        ServerWorld backrooms = server.getWorld(ModDimensions.THE_BACKROOMS);
        if (backrooms == null) {
            return;
        }

        int minY = backrooms.getBottomY();
        List<ServerPlayerEntity> toTeleport = new ArrayList<>();

        for (ServerPlayerEntity player : backrooms.getPlayers()) {
            UUID id = player.getUuid();
            if (player.getY() < minY) {
                int ticks = fallTicks.merge(id, 1, Integer::sum);
                if (ticks >= TELEPORT_DELAY_TICKS) {
                    toTeleport.add(player);
                    fallTicks.remove(id);
                }
            } else {
                fallTicks.remove(id);
            }
        }

        // Teleport after finishing iteration over backrooms.getPlayers() -
        // teleporting a player removes them from that list mid-iteration
        // and previously caused a ConcurrentModificationException.
        for (ServerPlayerEntity player : toTeleport) {
            teleportToOverworld(server, player);
        }

        // Clean up entries for players who left the dimension or disconnected.
        if (!fallTicks.isEmpty()) {
            Iterator<UUID> it = fallTicks.keySet().iterator();
            while (it.hasNext()) {
                UUID id = it.next();
                ServerPlayerEntity player = server.getPlayerManager().getPlayer(id);
                if (player == null || player.getWorld() != backrooms) {
                    it.remove();
                }
            }
        }
    }

    private static void teleportToOverworld(MinecraftServer server, ServerPlayerEntity player) {
        ServerWorld overworld = server.getWorld(World.OVERWORLD);
        if (overworld == null) {
            Backrooms.LOGGER.error("Cannot return {} from the void: overworld is not loaded", player.getName().getString());
            return;
        }

        Vec3d destination = new Vec3d(player.getX(), RETURN_Y, player.getZ());

        TeleportTarget target = new TeleportTarget(
                overworld,
                destination,
                Vec3d.ZERO,
                player.getYaw(),
                0.0f,
                Set.of(),
                TeleportTarget.NO_OP);

        player.teleportTo(target);
        Backrooms.LOGGER.info("Returned {} from the backrooms void to the overworld at y={}",
                player.getName().getString(), RETURN_Y);
    }
}
