package com.kintil555.backrooms;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shared constants used across common and client code.
 */
public final class Backrooms {
    public static final String MOD_ID = "backrooms";
    public static final Logger LOGGER = LoggerFactory.getLogger("Backrooms");

    private Backrooms() {
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
