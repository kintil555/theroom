package com.kintil555.backrooms.config;

import com.google.gson.GsonBuilder;
import com.kintil555.backrooms.Backrooms;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Simple JSON-backed config. Currently only holds toggles that don't
 * depend on the (not yet implemented) dimension generator. Fields for
 * room size / wall-set weighting are placeholders to be wired in once
 * the structure NBT pool exists.
 */
public final class BackroomsConfig {

    private static final Path PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("backrooms.json");

    public boolean enableAmbientSounds = true;
    public boolean enableEntityHum = true;
    public double almondWaterHealAmount = 4.0D;

    // Reserved for the future dimension generator.
    public int roomMinSize = 4;
    public int roomMaxSize = 12;
    public long structureWeightSeedOffset = 0L;

    private static BackroomsConfig instance;

    public static BackroomsConfig get() {
        if (instance == null) {
            load();
        }
        return instance;
    }

    public static void load() {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        if (Files.exists(PATH)) {
            try (Reader reader = Files.newBufferedReader(PATH, StandardCharsets.UTF_8)) {
                instance = gson.fromJson(reader, BackroomsConfig.class);
            } catch (IOException e) {
                Backrooms.LOGGER.error("Failed to read backrooms.json, using defaults", e);
                instance = new BackroomsConfig();
            }
        } else {
            instance = new BackroomsConfig();
        }
        save();
    }

    public static void save() {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Files.createDirectories(PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(PATH, StandardCharsets.UTF_8)) {
                gson.toJson(instance, writer);
            }
        } catch (IOException e) {
            Backrooms.LOGGER.error("Failed to write backrooms.json", e);
        }
    }
}
