package com.kintil555.backrooms;

import com.kintil555.backrooms.command.BackroomsCommand;
import com.kintil555.backrooms.config.BackroomsConfig;
import com.kintil555.backrooms.dimension.VoidFallHandler;
import com.kintil555.backrooms.nullblock.NullBlockEntry;
import com.kintil555.backrooms.registry.ModItemGroups;
import com.kintil555.backrooms.registry.ModItems;
import com.kintil555.backrooms.registry.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

/**
 * Common entrypoint. Registers items, item groups, sounds, and the
 * /backrooms enter|leave command. The dimension itself (type, generator,
 * jigsaw structure) is entirely data-driven under data/backrooms and
 * needs no runtime registration beyond the RegistryKey used by the
 * command to look the world up.
 */
public final class BackroomsMod implements ModInitializer {

    @Override
    public void onInitialize() {
        Backrooms.LOGGER.info("Initializing Backrooms mod");

        BackroomsConfig.load();
        ModSounds.register();
        ModItems.register();
        ModItemGroups.register();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                BackroomsCommand.register(dispatcher));
        NullBlockEntry.register();
        VoidFallHandler.register();

        Backrooms.LOGGER.info("Backrooms mod ready ({} items registered)", ModItems.count());
    }
}
