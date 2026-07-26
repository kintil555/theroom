package com.kintil555.backrooms.registry;

import com.kintil555.backrooms.Backrooms;
import com.kintil555.backrooms.item.AlmondWaterItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * All Backrooms items. Kept item-only for now: no blocks, no dimension
 * hooks. Structure/wall/floor randomization will consume these once the
 * generator exists.
 */
public final class ModItems {

    private static final List<Item> REGISTERED = new ArrayList<>();

    public static final Item ALMOND_WATER = register("almond_water",
            settings -> new AlmondWaterItem(settings.maxCount(16)));

    public static final Item DUCT_TAPE = register("duct_tape",
            settings -> new Item(settings.maxCount(64)));

    public static final Item BACKROOMS_KEY = register("backrooms_key",
            settings -> new Item(settings.maxCount(1)));

    public static final Item CRUMPLED_NOTE = register("crumpled_note",
            settings -> new Item(settings.maxCount(16)));

    private ModItems() {
    }

    private static Item register(String path, java.util.function.Function<Item.Settings, Item> factory) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Backrooms.MOD_ID, path));
        Item item = factory.apply(new Item.Settings().registryKey(key));
        Item registered = Registry.register(Registries.ITEM, key, item);
        REGISTERED.add(registered);
        return registered;
    }

    public static int count() {
        return REGISTERED.size();
    }

    public static void register() {
        Backrooms.LOGGER.info("Registered {} Backrooms items", REGISTERED.size());
    }
}
