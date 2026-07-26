package com.kintil555.backrooms.client;

import com.kintil555.backrooms.Backrooms;
import net.fabricmc.api.ClientModInitializer;

/**
 * Client-only entrypoint. Reserved for future rendering (fog, screen
 * overlays, ambience) once the dimension generator is implemented.
 */
public final class BackroomsModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Backrooms.LOGGER.info("Initializing Backrooms client");
    }
}
