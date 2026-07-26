package com.kintil555.backrooms;

import com.kintil555.backrooms.config.BackroomsConfig;
import com.kintil555.backrooms.registry.ModItemGroups;
import com.kintil555.backrooms.registry.ModItems;
import com.kintil555.backrooms.registry.ModSounds;
import net.fabricmc.api.ModInitializer;

/**
 * Common entrypoint. Registers items, item groups and sounds.
 * Dimension/chunk generator, entities and the noclip/level system are
 * intentionally left out until the structure NBTs are ready.
 */
public final class BackroomsMod implements ModInitializer {

    @Override
    public void onInitialize() {
        Backrooms.LOGGER.info("Initializing Backrooms mod");

        BackroomsConfig.load();
        ModSounds.register();
        ModItems.register();
        ModItemGroups.register();

        Backrooms.LOGGER.info("Backrooms mod ready ({} items registered)", ModItems.count());
    }
}
