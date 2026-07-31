package com.kintil555.backrooms.command;

import com.kintil555.backrooms.Backrooms;
import com.kintil555.backrooms.dimension.ModDimensions;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.Set;

/**
 * /backrooms enter [target]  - teleports the target (defaults to self)
 *                               into the_backrooms dimension.
 * /backrooms leave [target]  - teleports the target back to the overworld
 *                               spawn point.
 */
public final class BackroomsCommand {

    // Must stay in sync with "start_height.absolute" in
    // data/backrooms/worldgen/structure/level0.json (currently 50).
    // room_upstairs.nbt's floor sits at the structure's start Y, so entering
    // players need to spawn on top of that floor, not at a hardcoded low Y.
    private static final Vec3d ENTER_POS = new Vec3d(9.5, 51.0, 9.5);

    private BackroomsCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("backrooms")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("enter")
                        .executes(ctx -> enter(ctx.getSource(), ctx.getSource().getPlayerOrThrow()))
                        .then(CommandManager.argument("target", EntityArgumentType.player())
                                .executes(ctx -> enter(ctx.getSource(),
                                        EntityArgumentType.getPlayer(ctx, "target")))))
                .then(CommandManager.literal("leave")
                        .executes(ctx -> leave(ctx.getSource(), ctx.getSource().getPlayerOrThrow()))
                        .then(CommandManager.argument("target", EntityArgumentType.player())
                                .executes(ctx -> leave(ctx.getSource(),
                                        EntityArgumentType.getPlayer(ctx, "target"))))));
    }

    private static int enter(ServerCommandSource source, ServerPlayerEntity player) {
        ServerWorld destination = source.getServer().getWorld(ModDimensions.THE_BACKROOMS);
        if (destination == null) {
            source.sendError(Text.literal("The backrooms dimension is not loaded."));
            Backrooms.LOGGER.error("Attempted to teleport to {} but the world was not found", ModDimensions.THE_BACKROOMS.getValue());
            return 0;
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
        source.sendFeedback(() -> Text.literal("Teleported " + player.getName().getString() + " into the backrooms."), true);
        return 1;
    }

    private static int leave(ServerCommandSource source, ServerPlayerEntity player) {
        ServerWorld overworld = source.getServer().getWorld(World.OVERWORLD);
        if (overworld == null) {
            source.sendError(Text.literal("The overworld is not loaded."));
            return 0;
        }

        Vec3d spawnPos = Vec3d.ofBottomCenter(overworld.getSpawnPos());

        TeleportTarget target = new TeleportTarget(
                overworld,
                spawnPos,
                Vec3d.ZERO,
                player.getYaw(),
                0.0f,
                Set.of(),
                TeleportTarget.NO_OP);

        player.teleportTo(target);
        source.sendFeedback(() -> Text.literal("Teleported " + player.getName().getString() + " back to the overworld."), true);
        return 1;
    }
}
