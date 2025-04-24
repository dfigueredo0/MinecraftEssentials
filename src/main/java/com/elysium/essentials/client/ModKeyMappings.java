package com.elysium.essentials.client;

import com.elysium.essentials.Essentials;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {
    public static final String KEY_CATEGORY = "key.categories." + Essentials.MODID;

    public static final String KEY_HARVEST_ALL_TOGGLE = registerKey("harvest_all");
    public static final String KEY_SORT_INV = registerKey("sort_inv");

    public static final KeyMapping HARVEST_ALL_KEY = new KeyMapping(KEY_HARVEST_ALL_TOGGLE, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, KEY_CATEGORY);
    public static final KeyMapping SORT_INV_KEY = new KeyMapping(KEY_SORT_INV, KeyConflictContext.GUI, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY);

    private static String registerKey(String name) {
        return "key." + Essentials.MODID + "." + name;
    }
}
