package com.kintil555.backrooms.registry;

import com.kintil555.backrooms.Backrooms;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public final class ModItemGroups {

    public static final RegistryKey<ItemGroup> BACKROOMS_GROUP_KEY = RegistryKey.of(
            RegistryKeys.ITEM_GROUP, Backrooms.id("backrooms"));

    private ModItemGroups() {
    }

    public static void register() {
        ItemGroup group = FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModItems.ALMOND_WATER))
                .displayName(Text.translatable("itemGroup.backrooms"))
                .build();

        Registry.register(Registries.ITEM_GROUP, BACKROOMS_GROUP_KEY, group);

        ItemGroupEvents.modifyEntriesEvent(BACKROOMS_GROUP_KEY).register(entries -> {
            entries.add(ModItems.ALMOND_WATER);
            entries.add(ModItems.DUCT_TAPE);
        });

        Backrooms.LOGGER.info("Registered Backrooms item group");
    }
}
