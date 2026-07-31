package com.kintil555.backrooms.nullblock;

import com.kintil555.backrooms.Backrooms;
import com.kintil555.backrooms.dimension.ModDimensions;
import com.nullblock.remake.api.trigger.NullTriggerContext;
import com.nullblock.remake.api.trigger.NullTriggerRegistry;
import com.nullblock.remake.api.trigger.NullTriggerType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.Set;

/**
 * Wires NullBlock Remake's ENTITY_COLLISION trigger to a teleport into
 * the_backrooms. Fires one warning tick (particles + a distorted sound)
 * a moment before the teleport instead of yanking the player through
 * with zero cue, since a fully silent swap reads as broken/unfair rather
 * than as an intentional liminal-space hook.
 */
public final class NullBlockEntry {

    // Must stay in sync with "start_height.absolute" in
    // data/backrooms/worldgen/structure/level0.json (currently 50).
    private static final Vec3d ENTER_POS = new Vec3d(9.5, 51.0, 9.5);

    private NullBlockEntry() {
    }

    public static void register() {
        // ENTITY_COLLISION fires on actual contact; no radius config needed
        NullTriggerRegistry.register(NullTriggerType.ENTITY_COLLISION, NullBlockEntry::onCollision);
        Backrooms.LOGGER.info("Registered NullBlock -> Backrooms entry trigger");
    }

    private static void onCollision(NullTriggerContext ctx) {
        Entity entity = ctx.entity();
        if (!(entity instanceof ServerPlayerEntity player)) {
            return;
        }
        World level = ctx.level();
        if (!(level instanceof ServerWorld serverLevel)) {
            return;
        }

        warn(serverLevel, player);
        teleport(player);
    }

    private static void warn(ServerWorld level, ServerPlayerEntity player) {
        Vec3d pos = player.getPos();
        level.spawnParticles(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y + 1.0, pos.z, 30, 0.4, 0.6, 0.4, 0.01);
        level.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_PORTAL_TRIGGER,
                SoundCategory.BLOCKS, 0.6f, 0.6f);
    }

    private static void teleport(ServerPlayerEntity player) {
        ServerWorld destination = player.getServer().getWorld(ModDimensions.THE_BACKROOMS);
        if (destination == null) {
            Backrooms.LOGGER.error("NullBlock trigger fired but {} is not loaded", ModDimensions.THE_BACKROOMS.getValue());
            return;
        }

        TeleportTarget target = new TeleportTarget(
                destination,
                ENTER_POS,
                Vec3d.ZERO,
                player.getYaw(),
                0.0f,
                Set.of(),
                TeleportTarget.NO_OP);

        player.teleportTo(target);
    }
}
