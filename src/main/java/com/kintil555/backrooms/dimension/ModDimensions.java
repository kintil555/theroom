package com.kintil555.backrooms.dimension;

import com.kintil555.backrooms.Backrooms;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

/**
 * RegistryKey constants pointing at the backrooms dimension. The actual
 * dimension type / generator config live as JSON in
 * data/backrooms/dimension_type and data/backrooms/dimension, this class
 * only exposes the key so server code can teleport players into it.
 */
public final class ModDimensions {

    public static final RegistryKey<World> THE_BACKROOMS = RegistryKey.of(
            RegistryKeys.WORLD,
            Backrooms.id("the_backrooms"));

    private ModDimensions() {
    }
}
