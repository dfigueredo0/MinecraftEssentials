package com.elysium.essentials.client;

import com.elysium.essentials.Essentials;

public class ModKeyMappings {
    public static final String KEY_CATEGORY = "key.categories." + Essentials.MODID;

    private static String registerKey(String name) {
        return "key." + Essentials.MODID + "." + name;
    }
}
