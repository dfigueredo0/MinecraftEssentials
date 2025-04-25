package com.elysium.essentials.data.datagen;

import com.elysium.essentials.Essentials;
import com.elysium.essentials.client.ModKeyMappings;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, Essentials.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModKeyMappings.KEY_CATEGORY, "Elysium's Essentials");
        add(ModKeyMappings.KEY_HARVEST_ALL_TRIGGER, "Harvest All Ores/Logs");
        add(ModKeyMappings.KEY_SORT_INV, "Sort Inventory");
        add(ModKeyMappings.KEY_SHOW_RECIPE_BOOK, "Toggle Recipe Book in Inventory");
    }
}
