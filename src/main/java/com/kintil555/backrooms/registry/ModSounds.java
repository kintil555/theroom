package com.kintil555.backrooms.registry;

import com.kintil555.backrooms.Backrooms;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;

/**
 * Custom sound events. Only registered here; actual .ogg assets and the
 * ambience-playing system come later alongside the dimension generator.
 */
public final class ModSounds {

    public static final SoundEvent AMBIENT_HUM = register("ambient.hum");
    public static final SoundEvent ENTITY_STATIC_NOISE = register("entity.static_noise");

    private ModSounds() {
    }

    private static SoundEvent register(String path) {
        var id = Backrooms.id(path);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {
        Backrooms.LOGGER.info("Registered Backrooms sound events");
    }
}
